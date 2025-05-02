package com.inn.smart_reconciliation_api.configs.listeners;

import com.inn.smart_reconciliation_api.configs.entities.BaseEntity;
import com.inn.smart_reconciliation_api.configs.security.CustomUserDetails;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuditEntityListener {

    @PrePersist
    public void prePersist(BaseEntity entity) {

        Long userId = null;
        String username = "unknown";

        if (SecurityContextHolder.getContext().getAuthentication() != null &&
                SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomUserDetails userDetails) {
            userId = userDetails.getId();
            username = userDetails.getUsername();
        }

        entity.setCreatorId(userId);
        entity.setCreatorUsername(username);
    }

    @PreUpdate
    public void preUpdate(BaseEntity entity) {

        Long userId = null;
        String username = "unknown";
        if (SecurityContextHolder.getContext().getAuthentication() != null &&
                SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomUserDetails userDetails) {
            userId = userDetails.getId();
            username = userDetails.getUsername();
        }

        entity.setLastModifierId(userId);
        entity.setLastModifierUsername(username);
    }
}
