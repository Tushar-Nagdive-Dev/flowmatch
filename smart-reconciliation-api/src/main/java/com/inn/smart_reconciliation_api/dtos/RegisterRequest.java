package com.inn.smart_reconciliation_api.dtos;

import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(

    @NotBlank(message = "Username is required")
    String username,

    @NotBlank(message = "Email is required")
    String email,

    @NotBlank(message = "Password is required")
    String password
) {}
