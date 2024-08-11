package com.bank.subAccount.repo

import com.bank.subAccount.entity.SubAccount
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SubAccountRepository : JpaRepository<SubAccount, Long>{
}