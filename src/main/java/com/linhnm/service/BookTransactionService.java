package com.linhnm.service;

import com.linhnm.common.response.ErrorCode;
import com.linhnm.entity.BookTransactionEntity;
import com.linhnm.entity.TransactionLogEntity;
import com.linhnm.exception.BookTransactionNotFoundException;
import com.linhnm.exception.CommonException;
import com.linhnm.mapper.BookTransactionMapper;
import com.linhnm.model.dto.BookOrder;
import com.linhnm.model.query.FindBookTransactionsQuery;
import com.linhnm.model.request.BookTransactionRequest;
import com.linhnm.model.response.BookTransactionResponse;
import com.linhnm.repository.BookTransactionRepository;

import java.util.Date;
import java.util.Optional;

import com.linhnm.repository.TransactionLogRepository;
import com.linhnm.utils.PaymentClient;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookTransactionService {

    private final BookTransactionRepository bookTransactionRepository;
    private final TransactionLogRepository transactionLogRepository;
    private final BookTransactionMapper bookTransactionMapper;

    public Page<BookTransactionResponse> findAllBookTransactions(FindBookTransactionsQuery findBookTransactionsQuery) {

        // create Pageable instance
        Pageable pageable = createPageable(findBookTransactionsQuery);

        Page<BookTransactionEntity> bookTransactionsPage = bookTransactionRepository.findAll(pageable);

        return bookTransactionsPage.map(bookTransactionMapper::toResponse);
    }

    private Pageable createPageable(FindBookTransactionsQuery findBookTransactionsQuery) {
        int pageNo = Math.max(findBookTransactionsQuery.pageNo() - 1, 0);
        Sort sort = Sort.by(
                findBookTransactionsQuery.sortDir().equalsIgnoreCase(Sort.Direction.ASC.name())
                        ? Sort.Order.asc(findBookTransactionsQuery.sortBy())
                        : Sort.Order.desc(findBookTransactionsQuery.sortBy()));
        return PageRequest.of(pageNo, findBookTransactionsQuery.pageSize(), sort);
    }

    public Optional<BookTransactionResponse> findBookTransactionById(Long id) {
        return bookTransactionRepository.findById(id).map(bookTransactionMapper::toResponse);
    }

    @Transactional
    public BookTransactionResponse saveBookTransaction(BookTransactionRequest bookTransactionRequest) {
        BookTransactionEntity bookTransaction = bookTransactionMapper.toEntity(bookTransactionRequest);
        BookTransactionEntity savedBookTransaction = bookTransactionRepository.save(bookTransaction);
        return bookTransactionMapper.toResponse(savedBookTransaction);
    }

    @Transactional
    public BookTransactionResponse updateBookTransaction(Long id, BookTransactionRequest bookTransactionRequest) {
        BookTransactionEntity bookTransactionEntity =
                bookTransactionRepository.findById(id).orElseThrow(() -> new BookTransactionNotFoundException(id));

        // Update the bookTransaction object with data from bookTransactionRequest
        bookTransactionMapper.mapBookTransactionWithRequest(bookTransactionEntity, bookTransactionRequest);

        // Save the updated bookTransaction object
        BookTransactionEntity updatedBookTransaction = bookTransactionRepository.save(bookTransactionEntity);

        return bookTransactionMapper.toResponse(updatedBookTransaction);
    }

    @Transactional
    public void deleteBookTransactionById(Long id) {
        bookTransactionRepository.deleteById(id);
    }

    @Transactional
    public BookOrder.ConfirmTransactionResponse confirmOrder(BookOrder.ConfirmTransactionRequest request) {
        /**
         * This method is used to confirm an order
         * update transaction status to status
         * create transaction log
         */

        // Retrieve the transaction
        BookTransactionEntity bookTransactionEntity = bookTransactionRepository
                .findById(request.transactionId())
                .orElseThrow(() -> new CommonException(ErrorCode.TRANSACTION_NOT_FOUND));

        // Update the status of the transaction
        bookTransactionEntity.setStatus(request.status());
        bookTransactionEntity.setUpdatedAt(new Date());
        bookTransactionRepository.save(bookTransactionEntity);

        BookOrder.ConfirmTransactionResponse response = new BookOrder.ConfirmTransactionResponse(bookTransactionEntity.getId(), request.status());

        // Create a transaction log
        TransactionLogEntity transactionLogEntity = new TransactionLogEntity();
        transactionLogEntity.setTransactionId(bookTransactionEntity.getId());
        transactionLogEntity.setClient(PaymentClient.MANUAL);
        transactionLogEntity.setTransType("confirmOrder");
        transactionLogEntity.setRequestBody(request.toString());
        transactionLogEntity.setResponseBody(response.toString());
        transactionLogRepository.save(transactionLogEntity);

        return response;
    }
}
