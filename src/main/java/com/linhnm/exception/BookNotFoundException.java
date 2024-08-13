package com.linhnm.exception;

public class BookNotFoundException extends ResourceNotFoundException {

    public BookNotFoundException(Long id) {
        super("Book with Id '%d' not found".formatted(id));
    }
}
