package com.linhnm.service;

import com.linhnm.common.response.ErrorCode;
import com.linhnm.entity.BookTransactionEntity;
import com.linhnm.entity.PaymentCodeEntity;
import com.linhnm.entity.TransactionLogEntity;
import com.linhnm.exception.CommonException;
import com.linhnm.model.dto.BookOrder;
import com.linhnm.repository.BookTransactionRepository;
import com.linhnm.repository.PaymentCodeRepository;
import com.linhnm.repository.TransactionLogRepository;
import com.linhnm.utils.PaymentClient;
import com.linhnm.utils.TransactionStatus;
import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by linhnm on August 2024
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class BookOrderService {
    private final PaymentCodeRepository paymentCodeRepository;
    private final BookTransactionRepository bookTransactionRepository;
    private final TransactionLogRepository transactionLogRepository;
    private final Tracer tracer;

    @Transactional(rollbackFor = Exception.class)
    public BookOrder.Response orderBook(BookOrder.Request request) {
        /**
         * This method is used to book an order
         * get book_id from payment code
         * check if payment code is expired
         * create transaction with status created
         * create transaction log
         */

        // Retrieve the payment code
        PaymentCodeEntity paymentCodeEntity = paymentCodeRepository
                .findByCodeLock(request.paymentCode())
                .orElseThrow(() -> new CommonException(ErrorCode.PAYMENT_CODE_ERROR));

        // Check if the payment code is expired
        if (paymentCodeEntity.getExpiredAt().before(new Date())) {
            log.error("User {} pays with expired paymentCode {}", request.username(), request.paymentCode());
            throw new CommonException(ErrorCode.PAYMENT_CODE_ERROR);
        }

        Span span = tracer.currentSpan();
        String traceId = span.context().traceId();

        // Create a transaction with status created
        BookTransactionEntity bookTransactionEntity = new BookTransactionEntity();
        bookTransactionEntity.setBookId(paymentCodeEntity.getBookId());
        bookTransactionEntity.setRequestId(traceId);
        bookTransactionEntity.setCreatedBy(request.username());
        bookTransactionEntity.setAmount(paymentCodeEntity.getAmount());
        bookTransactionEntity.setStatus(TransactionStatus.CREATED);
        bookTransactionEntity = bookTransactionRepository.save(bookTransactionEntity);

        BookOrder.Response response = new BookOrder.Response(bookTransactionEntity.getId(), TransactionStatus.CREATED);

        // Create a transaction log
        TransactionLogEntity transactionLogEntity = new TransactionLogEntity();
        transactionLogEntity.setTransactionId(bookTransactionEntity.getId());
        transactionLogEntity.setClient(PaymentClient.MANUAL);
        transactionLogEntity.setTransType(PaymentClient.MANUAL);
        transactionLogEntity.setRequestBody(request.toString());
        transactionLogEntity.setResponseBody(response.toString());
        transactionLogRepository.save(transactionLogEntity);

        // Return the response
        return response;
    }


}
