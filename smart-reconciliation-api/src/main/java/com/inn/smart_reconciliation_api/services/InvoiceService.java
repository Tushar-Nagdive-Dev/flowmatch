package com.inn.smart_reconciliation_api.services;

import java.util.List;

import com.inn.smart_reconciliation_api.dtos.InvoiceRequest;
import com.inn.smart_reconciliation_api.dtos.InvoiceResponse;

public interface InvoiceService {
    
    InvoiceResponse createInvoice(InvoiceRequest request);

    InvoiceResponse getInvoice(Long id);

    List<InvoiceResponse> getAllInvoices();

    InvoiceResponse updateInvoice(Long id, InvoiceRequest request);

    void deleteInvoice(Long id);
}
