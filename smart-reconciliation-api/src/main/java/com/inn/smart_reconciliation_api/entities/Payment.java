package com.inn.smart_reconciliation_api.entities;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.inn.smart_reconciliation_api.configs.entities.BaseEntity;
import com.inn.smart_reconciliation_api.enums.PaymentStatus;

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
@Table(name = "payments")
@NoArgsConstructor
@AllArgsConstructor
public class Payment extends BaseEntity{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String paymentReference;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    private LocalDate paidDate;

    @Column(nullable = false)
    private String payerName;

    @Column(nullable = false)
    private Long userId; // Owner
    
}
