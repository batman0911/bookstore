package com.linhnm.mapper;

import com.linhnm.entity.PaymentCodeEntity;
import com.linhnm.model.request.PaymentCodeRequest;
import com.linhnm.model.response.PaymentCodeResponse;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PaymentCodeMapper {

    public PaymentCodeEntity toEntity(PaymentCodeRequest paymentCodeRequest) {
        PaymentCodeEntity paymentCodeEntity = new PaymentCodeEntity();
        paymentCodeEntity.setCode(paymentCodeRequest.text());
        return paymentCodeEntity;
    }

    public void mapPaymentCodeWithRequest(PaymentCodeEntity paymentCodeEntity, PaymentCodeRequest paymentCodeRequest) {
        paymentCodeEntity.setCode(paymentCodeRequest.text());
    }

    public PaymentCodeResponse toResponse(PaymentCodeEntity paymentCodeEntity) {
        return new PaymentCodeResponse(paymentCodeEntity.getId(), paymentCodeEntity.getCode());
    }

    public List<PaymentCodeResponse> toResponseList(List<PaymentCodeEntity> paymentCodeEntityList) {
        return paymentCodeEntityList.stream().map(this::toResponse).toList();
    }
}
