package com.linhnm.exception;

public class TransactionLogNotFoundException extends ResourceNotFoundException {

    public TransactionLogNotFoundException(Long id) {
        super("TransactionLog with Id '%d' not found".formatted(id));
    }
}
