package com.inn.smart_reconciliation_api.auditTrail.services.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.inn.smart_reconciliation_api.auditTrail.entity.AuditTrail;
import com.inn.smart_reconciliation_api.auditTrail.enums.AuditAction;
import com.inn.smart_reconciliation_api.auditTrail.enums.AuditEntityType;
import com.inn.smart_reconciliation_api.auditTrail.repo.AuditTrailRepository;
import com.inn.smart_reconciliation_api.auditTrail.services.AuditTrailService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuditTrailServiceImpl implements AuditTrailService{
    
    private final AuditTrailRepository auditTrailRepository;

    @Override
    public void recordAction(AuditEntityType entityType, Long entityId, AuditAction action, Long userId, String username) {

        AuditTrail auditTrail = AuditTrail.builder()
                .entityType(entityType)
                .entityId(entityId)
                .action(action)
                .performedBy(username)
                .performedAt(LocalDateTime.now())
                .userId(userId)
                .build();

        auditTrailRepository.save(auditTrail);
    }
}
