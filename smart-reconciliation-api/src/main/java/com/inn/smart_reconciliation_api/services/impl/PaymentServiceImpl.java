package com.inn.smart_reconciliation_api.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.inn.smart_reconciliation_api.auditTrail.enums.AuditAction;
import com.inn.smart_reconciliation_api.auditTrail.enums.AuditEntityType;
import com.inn.smart_reconciliation_api.auditTrail.services.AuditTrailService;
import com.inn.smart_reconciliation_api.configs.security.JwtUtil;
import com.inn.smart_reconciliation_api.dtos.PaymentRequest;
import com.inn.smart_reconciliation_api.dtos.PaymentResponse;
import com.inn.smart_reconciliation_api.entities.Payment;
import com.inn.smart_reconciliation_api.repo.PaymentRepository;
import com.inn.smart_reconciliation_api.services.PaymentService;
import com.inn.smart_reconciliation_api.services.ReconciliationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService{

    private final PaymentRepository paymentRepository;

    private final ReconciliationService reconciliationService;

    private final AuditTrailService auditTrailService;

    private final JwtUtil jwtUtil;

    @Override
    public PaymentResponse createPayment(PaymentRequest request) {

        Long userId = jwtUtil.getLoggedInUserId();

        Payment payment = Payment.builder()
                .paymentReference(request.paymentReference())
                .amount(request.amount())
                .status(request.status())
                .paidDate(request.paidDate())
                .payerName(request.payerName())
                .userId(userId)
                .build();

        Payment saved = paymentRepository.save(payment);
        reconciliationService.reconcilePayment(saved);
        auditTrailService.recordAction(AuditEntityType.PAYMENT, saved.getId(), AuditAction.CREATED, userId, jwtUtil.getLoggedInUsername());
        return mapToResponse(saved);
    }

    @Override
    public PaymentResponse getPayment(Long id) {
        Payment payment = paymentRepository.findById(id).orElseThrow(() -> new RuntimeException("Payment not found"));
        validateAccess(payment.getUserId());
        return mapToResponse(payment);
    }

    @Override
    public List<PaymentResponse> getAllPayments() {
        Long userId = jwtUtil.getLoggedInUserId();

        if (jwtUtil.isCurrentUserAdmin()) {
            return paymentRepository.findAll().stream().map(this::mapToResponse).toList();
        }

        return paymentRepository.findByUserId(userId).stream().map(this::mapToResponse).toList();
    }   

    @Override
    public PaymentResponse updatePayment(Long id, PaymentRequest request) {
        Payment payment = paymentRepository.findById(id).orElseThrow(() -> new RuntimeException("Payment not found"));
        validateAccess(payment.getUserId());

        payment.setPaymentReference(request.paymentReference());
        payment.setAmount(request.amount());
        payment.setStatus(request.status());
        payment.setPaidDate(request.paidDate());
        payment.setPayerName(request.payerName());

        auditTrailService.recordAction(AuditEntityType.PAYMENT, id, AuditAction.UPDATED, jwtUtil.getLoggedInUserId(), jwtUtil.getLoggedInUsername());
        return mapToResponse(paymentRepository.save(payment));
    }

    @Override
    public void deletePayment(Long id) {
        Payment payment = paymentRepository.findById(id).orElseThrow(() -> new RuntimeException("Payment not found"));
        validateAccess(payment.getUserId());
        auditTrailService.recordAction(AuditEntityType.PAYMENT, id, AuditAction.DELETED, jwtUtil.getLoggedInUserId(), jwtUtil.getLoggedInUsername());
        paymentRepository.delete(payment);
    }

    private PaymentResponse mapToResponse(Payment payment) {
        return new PaymentResponse(
                payment.getId(),
                payment.getPaymentReference(),
                payment.getAmount(),
                payment.getStatus(),
                payment.getPaidDate(),
                payment.getPayerName()
        );
    }

    private void validateAccess(Long ownerId) {
        if (!jwtUtil.isCurrentUserAdmin() && !ownerId.equals(jwtUtil.getLoggedInUserId())) {
            throw new RuntimeException("Access Denied");
        }
    }
}
