package com.inn.smart_reconciliation_api.invoice;

import com.inn.smart_reconciliation_api.auditTrail.enums.AuditAction;
import com.inn.smart_reconciliation_api.auditTrail.enums.AuditEntityType;
import com.inn.smart_reconciliation_api.auditTrail.services.AuditTrailService;
import com.inn.smart_reconciliation_api.configs.entities.exception.BusinessException;
import com.inn.smart_reconciliation_api.configs.entities.exception.NotFoundException;
import com.inn.smart_reconciliation_api.configs.security.JwtUtil;
import com.inn.smart_reconciliation_api.dtos.InvoiceRequest;
import com.inn.smart_reconciliation_api.dtos.InvoiceResponse;
import com.inn.smart_reconciliation_api.entities.Invoice;
import com.inn.smart_reconciliation_api.enums.InvoiceStatus;
import com.inn.smart_reconciliation_api.repo.InvoiceRepository;
import com.inn.smart_reconciliation_api.services.impl.InvoiceServiceImpl;

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

class InvoiceServiceImplTest {

    @Mock
    private InvoiceRepository invoiceRepository;

    @Mock
    private AuditTrailService auditTrailService;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private InvoiceServiceImpl invoiceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(jwtUtil.getLoggedInUserId()).thenReturn(1L);
        when(jwtUtil.getLoggedInUsername()).thenReturn("testuser");
        when(jwtUtil.isCurrentUserAdmin()).thenReturn(false);
    }

    @Test
    void testCreateInvoice() {
        InvoiceRequest request = new InvoiceRequest("INV-001", BigDecimal.valueOf(500), InvoiceStatus.PENDING, LocalDate.now(), LocalDate.now(), "Customer A");

        when(invoiceRepository.save(any(Invoice.class)))
                .thenReturn(Invoice.builder().id(1L).invoiceNumber("INV-001").amount(BigDecimal.valueOf(500)).userId(1L).build());

        InvoiceResponse response = invoiceService.createInvoice(request);

        assertNotNull(response);
        verify(auditTrailService).recordAction(eq(AuditEntityType.INVOICE), any(), eq(AuditAction.CREATED), eq(1L), eq("testuser"));
    }

    @Test
    void testGetInvoice_NotFound() {
        when(invoiceRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> invoiceService.getInvoice(1L));
    }

    @Test
    void testUpdateInvoice_AccessDenied() {
        Invoice invoice = Invoice.builder().id(1L).userId(99L).build();
        when(invoiceRepository.findById(1L)).thenReturn(Optional.of(invoice));

        assertThrows(BusinessException.class, () -> invoiceService.updateInvoice(1L, new InvoiceRequest("INV", BigDecimal.ZERO, InvoiceStatus.PENDING, LocalDate.now(), LocalDate.now(), "Customer")));
    }
}
