package com.inn.smart_reconciliation_api.configs.entities;

import java.time.Instant;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
    
    @Column(name = "creator_id", updatable = false)
    protected Long creatorId;

    @Column(name = "creator_username", updatable = false)
    protected String creatorUsername;

    @Column(name = "last_modifier_id")
    protected Long lastModifierId;

    @Column(name = "last_modifier_username")
    protected String lastModifierUsername;

    @CreatedDate
    @Column(name = "created_time", updatable = false)
    protected Instant createdTime;

    @LastModifiedDate
    @Column(name = "last_modified_time")
    protected Instant lastModifiedTime;
}
