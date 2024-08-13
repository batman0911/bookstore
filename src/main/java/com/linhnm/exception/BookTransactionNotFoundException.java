package com.linhnm.exception;

public class BookTransactionNotFoundException extends ResourceNotFoundException {

    public BookTransactionNotFoundException(Long id) {
        super("BookTransaction with Id '%d' not found".formatted(id));
    }
}
