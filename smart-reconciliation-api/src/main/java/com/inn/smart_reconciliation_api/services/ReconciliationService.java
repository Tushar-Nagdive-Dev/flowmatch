package com.inn.smart_reconciliation_api.services;

import java.util.List;

import com.inn.smart_reconciliation_api.dtos.ReconciliationRequest;
import com.inn.smart_reconciliation_api.dtos.ReconciliationResponse;
import com.inn.smart_reconciliation_api.entities.Payment;

public interface ReconciliationService {
    
    List<ReconciliationResponse> getAll();

    ReconciliationResponse getById(Long id);

    ReconciliationResponse create(ReconciliationRequest request);

    ReconciliationResponse update(Long id, ReconciliationRequest request);

    void reconcilePayment(Payment payment);

    void delete(Long id);
}
