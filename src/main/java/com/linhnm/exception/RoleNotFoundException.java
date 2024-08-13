package com.linhnm.exception;

public class RoleNotFoundException extends ResourceNotFoundException {

    public RoleNotFoundException(Long id) {
        super("Role with Id '%d' not found".formatted(id));
    }
}
