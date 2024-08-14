package com.linhnm.controller;

import com.linhnm.common.response.CommonResponse;
import com.linhnm.exception.PaymentCodeNotFoundException;
import com.linhnm.model.query.FindPaymentCodesQuery;
import com.linhnm.model.request.PaymentCodeRequest;
import com.linhnm.model.response.PaymentCodeResponse;
import com.linhnm.service.PaymentCodeService;
import com.linhnm.utils.AppConstants;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v${api.version}/payment-codes")
@Slf4j
@RequiredArgsConstructor
class PaymentCodeController {

    private final PaymentCodeService paymentCodeService;

    @GetMapping
    CommonResponse<List<PaymentCodeResponse>> getAllPaymentCodeList(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false)
                    int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false)
                    int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false)
                    String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false)
                    String sortDir) {
        FindPaymentCodesQuery findPaymentCodesQuery = new FindPaymentCodesQuery(pageNo, pageSize, sortBy, sortDir);
        Page<PaymentCodeResponse> paymentCodeResponsePage =
                paymentCodeService.findAllPaymentCodes(findPaymentCodesQuery);
        return CommonResponse.of(paymentCodeResponsePage);
    }

    @GetMapping("/{id}")
    CommonResponse<PaymentCodeResponse> getPaymentCodeById(@PathVariable Long id) {
        return paymentCodeService
                .findPaymentCodeById(id)
                .map(CommonResponse::of)
                .orElseThrow(() -> new PaymentCodeNotFoundException(id));
    }

    @PostMapping
    CommonResponse<PaymentCodeResponse> createPaymentCode(
            @RequestBody @Validated PaymentCodeRequest paymentCodeRequest) {
        PaymentCodeResponse response = paymentCodeService.savePaymentCode(paymentCodeRequest);
        return CommonResponse.of(response);
    }

    @PutMapping("/{id}")
    CommonResponse<PaymentCodeResponse> updatePaymentCode(
            @PathVariable Long id, @RequestBody @Valid PaymentCodeRequest paymentCodeRequest) {
        return CommonResponse.of(paymentCodeService.updatePaymentCode(id, paymentCodeRequest));
    }

    @DeleteMapping("/{id}")
    CommonResponse<PaymentCodeResponse> deletePaymentCode(@PathVariable Long id) {
        return paymentCodeService
                .findPaymentCodeById(id)
                .map(paymentCode -> {
                    paymentCodeService.deletePaymentCodeById(id);
                    return CommonResponse.of(paymentCode);
                })
                .orElseThrow(() -> new PaymentCodeNotFoundException(id));
    }
}
