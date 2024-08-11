package com.transfer.account_number.accountNumber.repository

import com.transfer.account_number.accountNumber.entity.AccountNumber
import org.springframework.data.jpa.repository.JpaRepository

interface AccountNumberRepository : JpaRepository<AccountNumber,String> {
    fun findByAccountNumber(accountNumber: String): AccountNumber?
    fun deleteByAccountNumber(accountNumber: String)
}