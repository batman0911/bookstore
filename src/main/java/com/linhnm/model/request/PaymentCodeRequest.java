package com.linhnm.model.request;

import jakarta.validation.constraints.NotEmpty;

public record PaymentCodeRequest(@NotEmpty(message = "Text cannot be empty") String text) {}
