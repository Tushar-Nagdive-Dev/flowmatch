package com.inn.smart_reconciliation_api.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inn.smart_reconciliation_api.dtos.InvoiceRequest;
import com.inn.smart_reconciliation_api.dtos.InvoiceResponse;
import com.inn.smart_reconciliation_api.services.InvoiceService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;

    @PreAuthorize("hasAnyRole('USER','ADMIN','FREE','PAID')")
    @PostMapping
    public ResponseEntity<InvoiceResponse> createInvoice(@Valid @RequestBody InvoiceRequest request) {
        return ResponseEntity.ok(invoiceService.createInvoice(request));
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN','FREE','PAID')")
    @GetMapping("/{id}")
    public ResponseEntity<InvoiceResponse> getInvoice(@PathVariable Long id) {
        return ResponseEntity.ok(invoiceService.getInvoice(id));
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN','FREE','PAID')")
    @GetMapping
    public ResponseEntity<List<InvoiceResponse>> getAllInvoices() {
        return ResponseEntity.ok(invoiceService.getAllInvoices());
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN','FREE','PAID')")
    @PutMapping("/{id}")
    public ResponseEntity<InvoiceResponse> updateInvoice(@PathVariable Long id, @RequestBody InvoiceRequest request) {
        return ResponseEntity.ok(invoiceService.updateInvoice(id, request));
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN','FREE','PAID')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteInvoice(@PathVariable Long id) {
        invoiceService.deleteInvoice(id);
        return ResponseEntity.ok("Invoice deleted successfully");
    }
}
