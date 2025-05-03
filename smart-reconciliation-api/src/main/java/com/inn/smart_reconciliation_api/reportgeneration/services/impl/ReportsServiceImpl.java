package com.inn.smart_reconciliation_api.reportgeneration.services.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.inn.smart_reconciliation_api.configs.security.JwtUtil;
import com.inn.smart_reconciliation_api.entities.Invoice;
import com.inn.smart_reconciliation_api.entities.Payment;
import com.inn.smart_reconciliation_api.repo.InvoiceRepository;
import com.inn.smart_reconciliation_api.repo.PaymentRepository;
import com.inn.smart_reconciliation_api.reportgeneration.services.ReportsService;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportsServiceImpl implements ReportsService{

    private final InvoiceRepository invoiceRepository;

    private final PaymentRepository paymentRepository;

    private final JwtUtil jwtUtil;

    @Override
    public ResponseEntity<InputStreamResource> exportInvoicesPdf() {
        List<Invoice> invoices = getInvoices();

        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            document.add(new Paragraph("Invoices Report"));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(4);
            table.addCell("Invoice Number");
            table.addCell("Amount");
            table.addCell("Status");
            table.addCell("Customer Name");

            for (Invoice invoice : invoices) {
                table.addCell(invoice.getInvoiceNumber());
                table.addCell(invoice.getAmount().toString());
                table.addCell(invoice.getStatus().name());
                table.addCell(invoice.getCustomerName());
            }

            document.add(table);
            document.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=invoices.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(new ByteArrayInputStream(out.toByteArray())));
    }

    @Override
    public ResponseEntity<InputStreamResource> exportInvoicesExcel() {
        List<Invoice> invoices = getInvoices();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Invoices");

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Invoice Number");
        header.createCell(1).setCellValue("Amount");
        header.createCell(2).setCellValue("Status");
        header.createCell(3).setCellValue("Customer Name");

        int rowIdx = 1;
        for (Invoice invoice : invoices) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(invoice.getInvoiceNumber());
            row.createCell(1).setCellValue(invoice.getAmount().toString());
            row.createCell(2).setCellValue(invoice.getStatus().name());
            row.createCell(3).setCellValue(invoice.getCustomerName());
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            workbook.write(out);
            workbook.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=invoices.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(new InputStreamResource(new ByteArrayInputStream(out.toByteArray())));
    }

    @Override
    public ResponseEntity<InputStreamResource> exportPaymentsPdf() {
        List<Payment> payments = getPayments();

        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            document.add(new Paragraph("Payments Report"));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(3);
            table.addCell("Payment Reference");
            table.addCell("Amount");
            table.addCell("Payer Name");

            for (Payment payment : payments) {
                table.addCell(payment.getPaymentReference());
                table.addCell(payment.getAmount().toString());
                table.addCell(payment.getPayerName());
            }

            document.add(table);
            document.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=payments.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(new ByteArrayInputStream(out.toByteArray())));
    }

    @Override
    public ResponseEntity<InputStreamResource> exportPaymentsExcel() {
        List<Payment> payments = getPayments();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Payments");

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Payment Reference");
        header.createCell(1).setCellValue("Amount");
        header.createCell(2).setCellValue("Payer Name");

        int rowIdx = 1;
        for (Payment payment : payments) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(payment.getPaymentReference());
            row.createCell(1).setCellValue(payment.getAmount().toString());
            row.createCell(2).setCellValue(payment.getPayerName());
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            workbook.write(out);
            workbook.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=payments.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(new InputStreamResource(new ByteArrayInputStream(out.toByteArray())));
    }

    private List<Invoice> getInvoices() {
        Long userId = jwtUtil.getLoggedInUserId();
        if (jwtUtil.isCurrentUserAdmin()) {
            return invoiceRepository.findAll();
        }
        return invoiceRepository.findByUserId(userId);
    }

    private List<Payment> getPayments() {
        Long userId = jwtUtil.getLoggedInUserId();
        if (jwtUtil.isCurrentUserAdmin()) {
            return paymentRepository.findAll();
        }
        return paymentRepository.findByUserId(userId);
    }
}
