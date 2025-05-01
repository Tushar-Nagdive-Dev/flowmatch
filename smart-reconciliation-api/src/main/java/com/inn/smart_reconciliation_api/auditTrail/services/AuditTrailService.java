package com.inn.smart_reconciliation_api.auditTrail.services;

import com.inn.smart_reconciliation_api.auditTrail.enums.AuditAction;
import com.inn.smart_reconciliation_api.auditTrail.enums.AuditEntityType;

public interface AuditTrailService {
    
    void recordAction(AuditEntityType entityType, Long entityId, AuditAction action, Long userId, String username);
}
