package com.linhnm.model.dto;

import jakarta.validation.constraints.NotEmpty;

/**
 * Created by linhnm on August 2024
 */
public class BookOrder {
    public record Request(@NotEmpty(message = "paymentCode cannot be empty") String paymentCode, String username) {}

    public record Response(Long transactionId, String status) {}
}
