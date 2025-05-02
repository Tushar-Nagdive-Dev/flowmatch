package com.inn.smart_reconciliation_api.payment;

import com.inn.smart_reconciliation_api.entities.Payment;
import com.inn.smart_reconciliation_api.enums.PaymentStatus;
import com.inn.smart_reconciliation_api.repo.PaymentRepository;

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
class PaymentRepositoryTest {

    @Autowired
    private PaymentRepository paymentRepository;

    @Test
    void testSavePayment() {
        Payment payment = Payment.builder()
                .paymentReference("PAY-100")
                .amount(BigDecimal.valueOf(500))
                .status(PaymentStatus.PENDING)
                .paidDate(LocalDate.now())
                .userId(1L)
                .payerName("PAY A")
                .build();

        Payment saved = paymentRepository.save(payment);
        assertNotNull(saved.getId());
    }

    @Test
    void testFindById() {
        Payment payment = Payment.builder()
                .paymentReference("PAY-200")
                .amount(BigDecimal.valueOf(600))
                .status(PaymentStatus.PENDING)
                .paidDate(LocalDate.now())
                .payerName("PAY B")
                .userId(1L)
                .build();

        Payment saved = paymentRepository.save(payment);
        Optional<Payment> fetched = paymentRepository.findById(saved.getId());
        assertTrue(fetched.isPresent());
    }

    @Test
    void testFindByUserId() {
        Payment payment = Payment.builder()
                .paymentReference("PAY-300")
                .amount(BigDecimal.valueOf(700))
                .status(PaymentStatus.PENDING)
                .paidDate(LocalDate.now())
                .userId(20L)
                .payerName("PAY C")
                .build();

        paymentRepository.save(payment);

        List<Payment> payments = paymentRepository.findByUserId(20L);
        assertFalse(payments.isEmpty());
    }
}
