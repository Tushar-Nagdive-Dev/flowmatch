package com.inn.smart_reconciliation_api.entities;

import java.time.LocalDate;

import com.inn.smart_reconciliation_api.configs.entities.BaseEntity;
import com.inn.smart_reconciliation_api.enums.ReconciliationStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reconciliations")
public class Reconciliation extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long invoiceId; // Can be null if unmatched

    @Column(nullable = false)
    private Long paymentId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReconciliationStatus status;

    private LocalDate matchedOn;

    @Column(nullable = false)
    private Long userId; // Owner of this reconciliation record

}
