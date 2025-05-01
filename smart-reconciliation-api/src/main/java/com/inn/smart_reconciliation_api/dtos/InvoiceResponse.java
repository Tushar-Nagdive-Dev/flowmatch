package com.inn.smart_reconciliation_api.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.inn.smart_reconciliation_api.enums.InvoiceStatus;

public record InvoiceResponse(
    Long id,
    String invoiceNumber,
    BigDecimal amount,
    InvoiceStatus status,
    LocalDate dueDate,
    LocalDate paidDate,
    String customerName
) {}
