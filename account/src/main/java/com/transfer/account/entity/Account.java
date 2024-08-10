package com.transfer.account.entity;

import com.transfer.account.dto.enums.AccountType;
import com.transfer.account.dto.enums.Branch;
import com.transfer.account.dto.enums.Country;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;

import static jakarta.persistence.EnumType.STRING;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
        name = "accounts",
        indexes = {
                @Index(
                        name = "idx_account_accountNumber",
                        columnList = "accountNumber"
                )
        }

)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id", nullable = false)
    private Long id;

    @Column(name = "country", nullable = false)
    @Enumerated(STRING)
    private Country country;

    @Column(name = "account_type", nullable = false)
    @Enumerated(STRING)
    private AccountType accountType;

    @Column(name = "branch", nullable = false)
    @Enumerated(STRING)
    private Branch branch;

    @Column(name = "account_number", nullable = false, unique = true)
    private String accountNumber;

    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at", updatable = false)
    @Builder.Default
    private Instant createdAt = Instant.now();

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at", updatable = true)
    @Builder.Default
    private Instant updatedAt = Instant.now();

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Favorite> favorites;
}
