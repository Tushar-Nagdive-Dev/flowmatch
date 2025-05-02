package com.inn.smart_reconciliation_api.payment;

import com.inn.smart_reconciliation_api.auditTrail.enums.AuditAction;
import com.inn.smart_reconciliation_api.auditTrail.enums.AuditEntityType;
import com.inn.smart_reconciliation_api.auditTrail.services.AuditTrailService;
import com.inn.smart_reconciliation_api.configs.security.JwtUtil;
import com.inn.smart_reconciliation_api.dtos.PaymentRequest;
import com.inn.smart_reconciliation_api.dtos.PaymentResponse;
import com.inn.smart_reconciliation_api.entities.Payment;
import com.inn.smart_reconciliation_api.enums.PaymentStatus;
import com.inn.smart_reconciliation_api.repo.PaymentRepository;
import com.inn.smart_reconciliation_api.services.ReconciliationService;
import com.inn.smart_reconciliation_api.services.impl.PaymentServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mockito.MockitoAnnotations;

class PaymentServiceImplTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private AuditTrailService auditTrailService;

    @Mock
    private ReconciliationService reconciliationService;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(jwtUtil.getLoggedInUserId()).thenReturn(1L);
        when(jwtUtil.getLoggedInUsername()).thenReturn("testuser");
        when(jwtUtil.isCurrentUserAdmin()).thenReturn(false);
    }

    @Test
    void testCreatePayment() {
        PaymentRequest request = new PaymentRequest("PAY-001", BigDecimal.valueOf(1000), PaymentStatus.PENDING, LocalDate.now(), "Payer A");

        when(paymentRepository.save(any(Payment.class)))
                .thenReturn(Payment.builder().id(1L).paymentReference("PAY-001").amount(BigDecimal.valueOf(1000)).userId(1L).build());

        PaymentResponse response = paymentService.createPayment(request);

        assertNotNull(response);
        verify(auditTrailService).recordAction(eq(AuditEntityType.PAYMENT), any(), eq(AuditAction.CREATED), eq(1L), eq("testuser"));
        verify(reconciliationService).reconcilePayment(any(Payment.class));
    }

    @Test
    void testGetPayment_NotFound() {
        when(paymentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> paymentService.getPayment(1L));
    }

    @Test
    void testUpdatePayment_AccessDenied() {
        Payment payment = Payment.builder().id(1L).userId(99L).build();
        when(paymentRepository.findById(1L)).thenReturn(Optional.of(payment));

        assertThrows(RuntimeException.class, () -> paymentService.updatePayment(1L, new PaymentRequest("PAY", BigDecimal.ZERO, PaymentStatus.PENDING, LocalDate.now(), "Payer")));
    }
}
