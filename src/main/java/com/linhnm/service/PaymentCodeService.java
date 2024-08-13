package com.linhnm.service;

import com.linhnm.entity.PaymentCodeEntity;
import com.linhnm.exception.PaymentCodeNotFoundException;
import com.linhnm.mapper.PaymentCodeMapper;
import com.linhnm.model.query.FindPaymentCodesQuery;
import com.linhnm.model.request.PaymentCodeRequest;
import com.linhnm.model.response.PaymentCodeResponse;
import com.linhnm.repository.PaymentCodeRepository;
import java.util.Optional;
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
public class PaymentCodeService {

    private final PaymentCodeRepository paymentCodeRepository;
    private final PaymentCodeMapper paymentCodeMapper;

    public Page<PaymentCodeResponse> findAllPaymentCodes(FindPaymentCodesQuery findPaymentCodesQuery) {

        // create Pageable instance
        Pageable pageable = createPageable(findPaymentCodesQuery);

        Page<PaymentCodeEntity> paymentCodesPage = paymentCodeRepository.findAll(pageable);

        return paymentCodesPage.map(paymentCodeMapper::toResponse);
    }

    private Pageable createPageable(FindPaymentCodesQuery findPaymentCodesQuery) {
        int pageNo = Math.max(findPaymentCodesQuery.pageNo() - 1, 0);
        Sort sort = Sort.by(
                findPaymentCodesQuery.sortDir().equalsIgnoreCase(Sort.Direction.ASC.name())
                        ? Sort.Order.asc(findPaymentCodesQuery.sortBy())
                        : Sort.Order.desc(findPaymentCodesQuery.sortBy()));
        return PageRequest.of(pageNo, findPaymentCodesQuery.pageSize(), sort);
    }

    public Optional<PaymentCodeResponse> findPaymentCodeById(Long id) {
        return paymentCodeRepository.findById(id).map(paymentCodeMapper::toResponse);
    }

    @Transactional
    public PaymentCodeResponse savePaymentCode(PaymentCodeRequest paymentCodeRequest) {
        PaymentCodeEntity paymentCode = paymentCodeMapper.toEntity(paymentCodeRequest);
        PaymentCodeEntity savedPaymentCode = paymentCodeRepository.save(paymentCode);
        return paymentCodeMapper.toResponse(savedPaymentCode);
    }

    @Transactional
    public PaymentCodeResponse updatePaymentCode(Long id, PaymentCodeRequest paymentCodeRequest) {
        PaymentCodeEntity paymentCodeEntity =
                paymentCodeRepository.findById(id).orElseThrow(() -> new PaymentCodeNotFoundException(id));

        // Update the paymentCode object with data from paymentCodeRequest
        paymentCodeMapper.mapPaymentCodeWithRequest(paymentCodeEntity, paymentCodeRequest);

        // Save the updated paymentCode object
        PaymentCodeEntity updatedPaymentCode = paymentCodeRepository.save(paymentCodeEntity);

        return paymentCodeMapper.toResponse(updatedPaymentCode);
    }

    @Transactional
    public void deletePaymentCodeById(Long id) {
        paymentCodeRepository.deleteById(id);
    }
}
