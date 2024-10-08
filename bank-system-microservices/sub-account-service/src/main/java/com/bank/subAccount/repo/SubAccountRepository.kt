package com.bank.subAccount.repo

import com.bank.subAccount.entity.SubAccount
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface SubAccountRepository : JpaRepository<SubAccount, Long>{

    @Query("""
        SELECT s
        FROM SubAccount s
        WHERE s.accountNumberId = :accountNumberId
    """)
    fun getSubAccountsForMainAccount(subAccountId : @ParameterName(name = "accountNumberId") String) : List<SubAccount>
}