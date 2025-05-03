package com.inn.smart_reconciliation_api.reportgeneration.controller;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inn.smart_reconciliation_api.reportgeneration.services.ReportsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportsController {

    private final ReportsService reportsService;

    @GetMapping("/invoices/pdf")
    public ResponseEntity<InputStreamResource> exportInvoicesPdf() {
        return reportsService.exportInvoicesPdf();
    }

    @GetMapping("/invoices/excel")
    public ResponseEntity<InputStreamResource> exportInvoicesExcel() {
        return reportsService.exportInvoicesExcel();
    }

    @GetMapping("/payments/pdf")
    public ResponseEntity<InputStreamResource> exportPaymentsPdf() {
        return reportsService.exportPaymentsPdf();
    }

    @GetMapping("/payments/excel")
    public ResponseEntity<InputStreamResource> exportPaymentsExcel() {
        return reportsService.exportPaymentsExcel();
    }
    
}
