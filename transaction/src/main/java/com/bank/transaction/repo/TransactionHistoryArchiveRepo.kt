package com.bank.transaction.repo

import com.bank.transaction.dto.enums.TransactionType
import com.bank.transaction.dto.request.TransactionCreateRequest
import com.bank.transaction.entity.TransactionHistoryArchive
import org.hibernate.validator.constraints.UUID
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TransactionHistoryArchiveRepo : JpaRepository<TransactionHistoryArchive, UUID> {

    fun findByAccountNumberIdOrderByTimestampDesc(accountNumberId : String) : List<TransactionHistoryArchive>
    fun findByAccountNumberIdAndTransactionType(accountNumberId: String, transactionType: TransactionType, pageable: Pageable) : Page<TransactionHistoryArchive>
}