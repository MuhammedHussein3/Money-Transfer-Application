package com.bank.subAccount.entity;

import com.bank.subAccount.dto.enums.SubAccountType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.Instant;

import static jakarta.persistence.EnumType.STRING;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "sub_accounts")
public class SubAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sub_account_id", nullable = false)
    private Long id;

    @Column(name = "account_number_id", nullable = false)
    private String accountNumberId;

    @Column(name = "sub_account_name", nullable = false)
    private String subAccountName;

    @Column(name = "sub_account_type", nullable = false)
    @Enumerated(STRING)
    private SubAccountType subAccountType;

    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

    @Column(name = "created_at", updatable = false)
    @ColumnDefault("CURRENT_TIMESTAMP")
    private Instant createdAt;

    @Column(name = "updated_at")
    @ColumnDefault("CURRENT_TIMESTAMP")
    private Instant updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now();
        updatedAt = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }
}
