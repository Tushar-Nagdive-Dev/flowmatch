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

import com.inn.smart_reconciliation_api.dtos.ReconciliationRequest;
import com.inn.smart_reconciliation_api.dtos.ReconciliationResponse;
import com.inn.smart_reconciliation_api.services.ReconciliationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reconciliations")
public class ReconciliationController {
    
    private final ReconciliationService reconciliationService;

    @PreAuthorize("hasAnyRole('USER','ADMIN','FREE','PAID')")
    @GetMapping
    public ResponseEntity<List<ReconciliationResponse>> getAll() {
        return ResponseEntity.ok(reconciliationService.getAll());
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN','FREE','PAID')")
    @GetMapping("/{id}")
    public ResponseEntity<ReconciliationResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(reconciliationService.getById(id));
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN','FREE','PAID')")
    @PostMapping
    public ResponseEntity<ReconciliationResponse> create(@RequestBody ReconciliationRequest request) {
        return ResponseEntity.ok(reconciliationService.create(request));
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN','FREE','PAID')")
    @PutMapping("/{id}")
    public ResponseEntity<ReconciliationResponse> update(@PathVariable Long id, @RequestBody ReconciliationRequest request) {
        return ResponseEntity.ok(reconciliationService.update(id, request));
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN','FREE','PAID')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        reconciliationService.delete(id);
        return ResponseEntity.ok("Deleted successfully");
    }
}
