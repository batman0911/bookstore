package com.linhnm.model.request;

import jakarta.validation.constraints.NotEmpty;

public record TransactionLogRequest(@NotEmpty(message = "Text cannot be empty") String text) {}
