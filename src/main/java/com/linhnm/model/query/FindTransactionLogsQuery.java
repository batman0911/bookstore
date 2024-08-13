package com.linhnm.model.query;

public record FindTransactionLogsQuery(int pageNo, int pageSize, String sortBy, String sortDir) {}
