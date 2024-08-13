package com.linhnm.exception;

public class AuthorNotFoundException extends ResourceNotFoundException {

    public AuthorNotFoundException(Long id) {
        super("Author with Id '%d' not found".formatted(id));
    }
}
