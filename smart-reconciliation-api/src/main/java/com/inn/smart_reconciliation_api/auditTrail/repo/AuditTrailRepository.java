package com.inn.smart_reconciliation_api.auditTrail.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inn.smart_reconciliation_api.auditTrail.entity.AuditTrail;

@Repository
public interface AuditTrailRepository extends JpaRepository<AuditTrail, Long>{
    
}
