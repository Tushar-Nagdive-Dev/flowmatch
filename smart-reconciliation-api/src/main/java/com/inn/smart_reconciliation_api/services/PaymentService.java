package com.inn.smart_reconciliation_api.services;

import java.util.List;

import com.inn.smart_reconciliation_api.dtos.PaymentRequest;
import com.inn.smart_reconciliation_api.dtos.PaymentResponse;

public interface PaymentService {
    
    PaymentResponse createPayment(PaymentRequest request);

    PaymentResponse getPayment(Long id);

    List<PaymentResponse> getAllPayments();

    PaymentResponse updatePayment(Long id, PaymentRequest request);

    void deletePayment(Long id);
}
