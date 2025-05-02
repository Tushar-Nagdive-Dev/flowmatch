package com.inn.smart_reconciliation_api.invoice;

import com.inn.smart_reconciliation_api.entities.Invoice;
import com.inn.smart_reconciliation_api.enums.InvoiceStatus;
import com.inn.smart_reconciliation_api.repo.InvoiceRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class InvoiceRepositoryTest {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Test
    void testSaveInvoice() {
        Invoice invoice = Invoice.builder()
                .invoiceNumber("INV-100")
                .amount(BigDecimal.valueOf(500))
                .status(InvoiceStatus.PENDING)
                .dueDate(LocalDate.now())
                .userId(1L)
                .customerName("CUST C")
                .build();

        Invoice saved = invoiceRepository.save(invoice);
        assertNotNull(saved.getId());
    }

    @Test
    void testFindById() {
        Invoice invoice = Invoice.builder()
                .invoiceNumber("INV-200")
                .amount(BigDecimal.valueOf(600))
                .status(InvoiceStatus.PENDING)
                .dueDate(LocalDate.now())
                .customerName("CUST A")
                .userId(1L)
                .build();

        Invoice saved = invoiceRepository.save(invoice);

        Optional<Invoice> fetched = invoiceRepository.findById(saved.getId());
        assertTrue(fetched.isPresent());
    }

    @Test
    void testFindByUserId() {
        Invoice invoice = Invoice.builder()
                .invoiceNumber("INV-300")
                .amount(BigDecimal.valueOf(700))
                .status(InvoiceStatus.PENDING)
                .dueDate(LocalDate.now())
                .userId(10L)
                .customerName("CUST B")
                .build();

        invoiceRepository.save(invoice);

        List<Invoice> invoices = invoiceRepository.findByUserId(10L);
        assertFalse(invoices.isEmpty());
    }
}
