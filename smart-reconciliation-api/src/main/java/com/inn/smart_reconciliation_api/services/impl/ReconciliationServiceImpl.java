package com.inn.smart_reconciliation_api.services.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.inn.smart_reconciliation_api.auditTrail.enums.AuditAction;
import com.inn.smart_reconciliation_api.auditTrail.enums.AuditEntityType;
import com.inn.smart_reconciliation_api.auditTrail.services.AuditTrailService;
import com.inn.smart_reconciliation_api.configs.security.JwtUtil;
import com.inn.smart_reconciliation_api.dtos.ReconciliationRequest;
import com.inn.smart_reconciliation_api.dtos.ReconciliationResponse;
import com.inn.smart_reconciliation_api.entities.Invoice;
import com.inn.smart_reconciliation_api.entities.Payment;
import com.inn.smart_reconciliation_api.entities.Reconciliation;
import com.inn.smart_reconciliation_api.enums.ReconciliationStatus;
import com.inn.smart_reconciliation_api.repo.InvoiceRepository;
import com.inn.smart_reconciliation_api.repo.ReconciliationRepository;
import com.inn.smart_reconciliation_api.services.ReconciliationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReconciliationServiceImpl implements ReconciliationService{
    
    private final ReconciliationRepository reconciliationRepository;

    private final InvoiceRepository invoiceRepository;

    private final AuditTrailService auditTrailService;

    private final JwtUtil jwtUtil;

    @Override
    public List<ReconciliationResponse> getAll() {
        Long userId = jwtUtil.getLoggedInUserId();

        if (jwtUtil.isCurrentUserAdmin()) {
            return reconciliationRepository.findAll().stream().map(this::mapToResponse).toList();
        }

        return reconciliationRepository.findByUserId(userId).stream().map(this::mapToResponse).toList();
    }

    @Override
    public ReconciliationResponse getById(Long id) {
        Reconciliation rec = reconciliationRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));

        validateAccess(rec.getUserId());

        return mapToResponse(rec);
    }

    @Override
    public ReconciliationResponse create(ReconciliationRequest request) {

        Long userId = jwtUtil.getLoggedInUserId();

        Reconciliation reconciliation = Reconciliation.builder()
                .invoiceId(request.invoiceId())
                .paymentId(request.paymentId())
                .status(request.status())
                .matchedOn(request.matchedOn())
                .userId(userId)
                .build();
        Reconciliation saved = reconciliationRepository.save(reconciliation);
        auditTrailService.recordAction(AuditEntityType.RECONCILIATION, saved.getId(), AuditAction.CREATED, userId, jwtUtil.getLoggedInUsername());
        return mapToResponse(saved);
    }

    @Override
    public ReconciliationResponse update(Long id, ReconciliationRequest request) {

        Reconciliation rec = reconciliationRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));

        validateAccess(rec.getUserId());

        rec.setInvoiceId(request.invoiceId());
        rec.setPaymentId(request.paymentId());
        rec.setStatus(request.status());
        rec.setMatchedOn(request.matchedOn());
        Reconciliation updated = reconciliationRepository.save(rec);
        auditTrailService.recordAction(AuditEntityType.RECONCILIATION, updated.getId(), AuditAction.UPDATED, jwtUtil.getLoggedInUserId(), jwtUtil.getLoggedInUsername());
        return mapToResponse(updated);
    }

    @Override
    public void delete(Long id) {

        Reconciliation rec = reconciliationRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));

        validateAccess(rec.getUserId());
        auditTrailService.recordAction(AuditEntityType.RECONCILIATION, id, AuditAction.DELETED, jwtUtil.getLoggedInUserId(), jwtUtil.getLoggedInUsername());
        reconciliationRepository.delete(rec);
    }

    private void validateAccess(Long ownerId) {
        if (!jwtUtil.isCurrentUserAdmin() && !ownerId.equals(jwtUtil.getLoggedInUserId())) {
            throw new RuntimeException("Access Denied");
        }
    }

    private ReconciliationResponse mapToResponse(Reconciliation rec) {
        return new ReconciliationResponse(
                rec.getId(),
                rec.getInvoiceId(),
                rec.getPaymentId(),
                rec.getStatus(),
                rec.getMatchedOn()
        );
    }

    // SMART MATCHING ENGINE

    public void reconcilePayment(Payment payment) {

        List<Invoice> invoices = invoiceRepository.findByUserId(payment.getUserId())
                .stream()
                .filter(inv -> inv.getAmount().compareTo(payment.getAmount()) == 0)
                .filter(inv -> inv.getStatus().name().equals("PENDING"))
                .toList();

        ReconciliationStatus status;
        Long invoiceId = null;

        if (!invoices.isEmpty()) {
            invoiceId = invoices.get(0).getId();
            status = ReconciliationStatus.MATCHED;
        } else {
            status = ReconciliationStatus.UNMATCHED;
        }

        Reconciliation reconciliation = Reconciliation.builder()
                .invoiceId(invoiceId)
                .paymentId(payment.getId())
                .status(status)
                .matchedOn(LocalDate.now())
                .userId(payment.getUserId())
                .build();

        reconciliationRepository.save(reconciliation);
    }
}
