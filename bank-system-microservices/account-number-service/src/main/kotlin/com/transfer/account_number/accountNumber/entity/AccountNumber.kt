package com.transfer.account_number.accountNumber.entity


import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.NoArgsConstructor
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "account_numbers")
data class AccountNumber(
    @Id
    @Column(name = "account_number")
    val accountNumber: String? = null,

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    val createdAt: LocalDateTime

) {

}

