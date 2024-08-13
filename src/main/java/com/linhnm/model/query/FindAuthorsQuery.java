package com.linhnm.model.query;

public record FindAuthorsQuery(int pageNo, int pageSize, String sortBy, String sortDir) {}
