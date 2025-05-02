package com.inn.smart_reconciliation_api.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.inn.smart_reconciliation_api.enums.InvoiceStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record InvoiceRequest(
    @NotBlank(message = "Invoice number is required")
    String invoiceNumber,

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    BigDecimal amount,

    @NotNull(message = "Invoice status is required")
    InvoiceStatus status,

    @NotNull(message = "Due date is required")
    LocalDate dueDate,
    LocalDate paidDate,

    @NotBlank(message = "Customer name is required")
    String customerName
) {}
