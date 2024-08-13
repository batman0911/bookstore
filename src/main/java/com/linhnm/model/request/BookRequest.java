package com.linhnm.model.request;

import jakarta.validation.constraints.NotEmpty;

public record BookRequest(@NotEmpty(message = "Text cannot be empty") String text) {}
