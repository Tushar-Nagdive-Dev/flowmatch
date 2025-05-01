package com.inn.smart_reconciliation_api.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inn.smart_reconciliation_api.entities.Reconciliation;

@Repository
public interface ReconciliationRepository extends JpaRepository<Reconciliation, Long> {
    
    List<Reconciliation> findByUserId(Long userId);
}
