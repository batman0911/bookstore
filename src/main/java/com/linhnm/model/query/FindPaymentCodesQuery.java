package com.linhnm.model.query;

public record FindPaymentCodesQuery(int pageNo, int pageSize, String sortBy, String sortDir) {}
