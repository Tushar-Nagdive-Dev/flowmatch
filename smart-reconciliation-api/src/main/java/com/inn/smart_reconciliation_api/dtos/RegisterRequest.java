package com.inn.smart_reconciliation_api.dtos;

public record RegisterRequest(
    String username,
    String email,
    String password
) {}
