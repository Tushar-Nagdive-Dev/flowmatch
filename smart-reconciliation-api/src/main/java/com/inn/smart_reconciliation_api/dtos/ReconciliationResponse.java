package com.inn.smart_reconciliation_api.dtos;

import java.time.LocalDate;

import com.inn.smart_reconciliation_api.enums.ReconciliationStatus;

public record ReconciliationResponse(
    Long id,
    Long invoiceId,
    Long paymentId,
    ReconciliationStatus status,
    LocalDate matchedOn
) {}
