package com.inn.smart_reconciliation_api.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.inn.smart_reconciliation_api.enums.PaymentStatus;

public record PaymentRequest(
    String paymentReference,
    BigDecimal amount,
    PaymentStatus status,
    LocalDate paidDate,
    String payerName
) {}
