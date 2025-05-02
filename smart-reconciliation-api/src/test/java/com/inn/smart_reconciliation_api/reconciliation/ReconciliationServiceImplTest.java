package com.inn.smart_reconciliation_api.reconciliation;

import com.inn.smart_reconciliation_api.auditTrail.enums.AuditAction;
import com.inn.smart_reconciliation_api.auditTrail.enums.AuditEntityType;
import com.inn.smart_reconciliation_api.auditTrail.services.AuditTrailService;
import com.inn.smart_reconciliation_api.configs.security.JwtUtil;
import com.inn.smart_reconciliation_api.dtos.ReconciliationRequest;
import com.inn.smart_reconciliation_api.dtos.ReconciliationResponse;
import com.inn.smart_reconciliation_api.entities.Reconciliation;
import com.inn.smart_reconciliation_api.enums.ReconciliationStatus;
import com.inn.smart_reconciliation_api.repo.InvoiceRepository;
import com.inn.smart_reconciliation_api.repo.ReconciliationRepository;
import com.inn.smart_reconciliation_api.services.impl.ReconciliationServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mockito.MockitoAnnotations;

class ReconciliationServiceImplTest {

    @Mock
    private ReconciliationRepository reconciliationRepository;

    @Mock
    private InvoiceRepository invoiceRepository;

    @Mock
    private AuditTrailService auditTrailService;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private ReconciliationServiceImpl reconciliationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(jwtUtil.getLoggedInUserId()).thenReturn(1L);
        when(jwtUtil.getLoggedInUsername()).thenReturn("testuser");
        when(jwtUtil.isCurrentUserAdmin()).thenReturn(false);
    }

    @Test
    void testCreateReconciliation() {
        ReconciliationRequest request = new ReconciliationRequest(1L, 1L, ReconciliationStatus.MATCHED, LocalDate.now());

        when(reconciliationRepository.save(any(Reconciliation.class)))
                .thenReturn(Reconciliation.builder().id(1L).invoiceId(1L).paymentId(1L).userId(1L).status(ReconciliationStatus.MATCHED).build());

        ReconciliationResponse response = reconciliationService.create(request);

        assertNotNull(response);
        verify(auditTrailService).recordAction(eq(AuditEntityType.RECONCILIATION), any(), eq(AuditAction.CREATED), eq(1L), eq("testuser"));
    }

    @Test
    void testGetById_NotFound() {
        when(reconciliationRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> reconciliationService.getById(1L));
    }

    @Test
    void testUpdate_AccessDenied() {
        Reconciliation rec = Reconciliation.builder().id(1L).userId(99L).build();
        when(reconciliationRepository.findById(1L)).thenReturn(Optional.of(rec));

        assertThrows(RuntimeException.class, () -> reconciliationService.update(1L, new ReconciliationRequest(1L, 1L, ReconciliationStatus.MATCHED, LocalDate.now())));
    }
}
