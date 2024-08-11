package com.bank.transaction.entity;

import com.bank.transaction.dto.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import static jakarta.persistence.EnumType.STRING;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(
        name = "transaction_history_archive",
        indexes = {
                @Index(
                        name = "idx_account_number_id",
                        columnList = "accountNumberId"
                ),
                @Index(
                        name = "idx_timestamp",
                        columnList = "timestamp"
                )
        }
)
public class TransactionHistoryArchive {

    @Id
    @UuidGenerator
    @Column(name = "transaction_id",updatable = false, unique = true, nullable = false)
    private UUID transactionId;

    @Column(name = "account_number_id", nullable = false)
    private String accountNumberId;

    @Enumerated(STRING)
    @Column(name = "transaction_type", nullable = false)
    private TransactionType transactionType;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "timestamp", updatable = false,nullable = false)
    @Builder.Default
    private Instant timestamp = Instant.now();

    @Column(name = "description")
    private String description;

    @Column(name = "balance_after_transaction", nullable = false)
    private BigDecimal balanceAfterTransaction;
}
