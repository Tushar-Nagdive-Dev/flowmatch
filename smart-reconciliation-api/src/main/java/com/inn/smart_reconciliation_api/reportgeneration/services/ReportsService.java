package com.inn.smart_reconciliation_api.reportgeneration.services;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;

public interface ReportsService {
    ResponseEntity<InputStreamResource> exportInvoicesPdf();
    ResponseEntity<InputStreamResource> exportInvoicesExcel();
    ResponseEntity<InputStreamResource> exportPaymentsPdf();
    ResponseEntity<InputStreamResource> exportPaymentsExcel();
}
