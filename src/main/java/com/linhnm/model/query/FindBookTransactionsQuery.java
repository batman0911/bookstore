package com.linhnm.model.query;

public record FindBookTransactionsQuery(int pageNo, int pageSize, String sortBy, String sortDir) {}
