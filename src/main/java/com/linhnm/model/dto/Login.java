package com.linhnm.model.dto;

import jakarta.validation.constraints.NotEmpty;

public class Login {
    public record Request(
            @NotEmpty(message = "username cannot be empty") String username,
            @NotEmpty(message = "password cannot be empty") String password) {}

    public record Response(String accessToken, String refreshToken, Long expiresAt) {}
}
