package com.inn.smart_reconciliation_api.reconciliation;

import com.inn.smart_reconciliation_api.entities.Reconciliation;
import com.inn.smart_reconciliation_api.enums.ReconciliationStatus;
import com.inn.smart_reconciliation_api.repo.ReconciliationRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ReconciliationRepositoryTest {

    @Autowired
    private ReconciliationRepository reconciliationRepository;

    @Test
    void testSaveReconciliation() {
        Reconciliation reconciliation = Reconciliation.builder()
                .invoiceId(1L)
                .paymentId(1L)
                .status(ReconciliationStatus.MATCHED)
                .matchedOn(LocalDate.now())
                .userId(1L)
                .build();

        Reconciliation saved = reconciliationRepository.save(reconciliation);
        assertNotNull(saved.getId());
    }

    @Test
    void testFindById() {
        Reconciliation reconciliation = Reconciliation.builder()
                .invoiceId(2L)
                .paymentId(2L)
                .status(ReconciliationStatus.MATCHED)
                .matchedOn(LocalDate.now())
                .userId(1L)
                .build();

        Reconciliation saved = reconciliationRepository.save(reconciliation);
        Optional<Reconciliation> fetched = reconciliationRepository.findById(saved.getId());
        assertTrue(fetched.isPresent());
    }

    @Test
    void testFindByUserId() {
        Reconciliation reconciliation = Reconciliation.builder()
                .invoiceId(3L)
                .paymentId(3L)
                .status(ReconciliationStatus.UNMATCHED)
                .matchedOn(LocalDate.now())
                .userId(30L)
                .build();

        reconciliationRepository.save(reconciliation);

        List<Reconciliation> reconciliations = reconciliationRepository.findByUserId(30L);
        assertFalse(reconciliations.isEmpty());
    }
}
