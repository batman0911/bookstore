package com.linhnm.exception;

public class PaymentCodeNotFoundException extends ResourceNotFoundException {

    public PaymentCodeNotFoundException(Long id) {
        super("PaymentCode with Id '%d' not found".formatted(id));
    }
}
