package com.inn.smart_reconciliation_api.dtos;

public record LoginRequest(
    String username,
    String password
) {}
