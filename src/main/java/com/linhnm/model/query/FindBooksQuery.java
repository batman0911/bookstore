package com.linhnm.model.query;

public record FindBooksQuery(int pageNo, int pageSize, String sortBy, String sortDir) {}
