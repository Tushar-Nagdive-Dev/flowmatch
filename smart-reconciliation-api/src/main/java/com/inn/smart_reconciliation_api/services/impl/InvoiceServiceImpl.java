package com.inn.smart_reconciliation_api.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.inn.smart_reconciliation_api.auditTrail.enums.AuditAction;
import com.inn.smart_reconciliation_api.auditTrail.enums.AuditEntityType;
import com.inn.smart_reconciliation_api.auditTrail.services.AuditTrailService;
import com.inn.smart_reconciliation_api.configs.security.JwtUtil;
import com.inn.smart_reconciliation_api.dtos.InvoiceRequest;
import com.inn.smart_reconciliation_api.dtos.InvoiceResponse;
import com.inn.smart_reconciliation_api.entities.Invoice;
import com.inn.smart_reconciliation_api.repo.InvoiceRepository;
import com.inn.smart_reconciliation_api.services.InvoiceService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService{
    
    private final InvoiceRepository invoiceRepository;

    private final AuditTrailService auditTrailService;

    private final JwtUtil jwtUtil;

    @Override
    public InvoiceResponse createInvoice(InvoiceRequest request) {

        Long userId = jwtUtil.getLoggedInUserId();

        Invoice invoice = Invoice.builder()
                .invoiceNumber(request.invoiceNumber())
                .amount(request.amount())
                .status(request.status())
                .dueDate(request.dueDate())
                .paidDate(request.paidDate())
                .customerName(request.customerName())
                .userId(userId)
                .build();

        Invoice saved = invoiceRepository.save(invoice);
        auditTrailService.recordAction(AuditEntityType.INVOICE, saved.getId(), AuditAction.CREATED, userId, jwtUtil.getLoggedInUsername());
        return mapToResponse(saved);
    }

    @Override
    public InvoiceResponse getInvoice(Long id) {

        Invoice invoice = invoiceRepository.findById(id).orElseThrow(() -> new RuntimeException("Invoice not found"));

        validateAccess(invoice.getUserId());

        return mapToResponse(invoice);
    }

    @Override
    public List<InvoiceResponse> getAllInvoices() {

        Long userId = jwtUtil.getLoggedInUserId();

        if (jwtUtil.isCurrentUserAdmin()) {
            return invoiceRepository.findAll().stream().map(this::mapToResponse).toList();
        }

        return invoiceRepository.findByUserId(userId).stream().map(this::mapToResponse).toList();
    }

    @Override
    public InvoiceResponse updateInvoice(Long id, InvoiceRequest request) {

        Invoice invoice = invoiceRepository.findById(id).orElseThrow(() -> new RuntimeException("Invoice not found"));

        validateAccess(invoice.getUserId());

        invoice.setInvoiceNumber(request.invoiceNumber());
        invoice.setAmount(request.amount());
        invoice.setStatus(request.status());
        invoice.setDueDate(request.dueDate());
        invoice.setPaidDate(request.paidDate());
        invoice.setCustomerName(request.customerName());
        auditTrailService.recordAction(AuditEntityType.INVOICE, id, AuditAction.UPDATED, jwtUtil.getLoggedInUserId(), jwtUtil.getLoggedInUsername());
        return mapToResponse(invoiceRepository.save(invoice));
    }

    @Override
    public void deleteInvoice(Long id) {

        Invoice invoice = invoiceRepository.findById(id).orElseThrow(() -> new RuntimeException("Invoice not found"));

        validateAccess(invoice.getUserId());
        auditTrailService.recordAction(AuditEntityType.INVOICE, id, AuditAction.DELETED, jwtUtil.getLoggedInUserId(), jwtUtil.getLoggedInUsername());
        invoiceRepository.delete(invoice);
    }

    private InvoiceResponse mapToResponse(Invoice invoice) {
        return new InvoiceResponse(
            invoice.getId(),
            invoice.getInvoiceNumber(),
            invoice.getAmount(),
            invoice.getStatus(),
            invoice.getDueDate(),
            invoice.getPaidDate(),
            invoice.getCustomerName()
        );
    }

    private void validateAccess(Long ownerId) {
        if (!jwtUtil.isCurrentUserAdmin() && !ownerId.equals(jwtUtil.getLoggedInUserId())) {
            throw new RuntimeException("Access Denied");
        }
    }
}
