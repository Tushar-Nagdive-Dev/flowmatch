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

import com.inn.smart_reconciliation_api.dtos.PaymentRequest;
import com.inn.smart_reconciliation_api.dtos.PaymentResponse;
import com.inn.smart_reconciliation_api.services.PaymentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {
    
    private final PaymentService paymentService;

    @PreAuthorize("hasAnyRole('USER','ADMIN','FREE','PAID')")
    @PostMapping
    public ResponseEntity<PaymentResponse> createPayment(@RequestBody PaymentRequest request) {
        return ResponseEntity.ok(paymentService.createPayment(request));
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN','FREE','PAID')")
    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponse> getPayment(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.getPayment(id));
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN','FREE','PAID')")
    @GetMapping
    public ResponseEntity<List<PaymentResponse>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN','FREE','PAID')")
    @PutMapping("/{id}")
    public ResponseEntity<PaymentResponse> updatePayment(@PathVariable Long id, @RequestBody PaymentRequest request) {
        return ResponseEntity.ok(paymentService.updatePayment(id, request));
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN','FREE','PAID')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return ResponseEntity.ok("Payment deleted successfully");
    }
}
