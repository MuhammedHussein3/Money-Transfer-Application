package com.bank.subAccount.service

import com.bank.subAccount.dto.SubAccountDetailsResponse
import com.bank.subAccount.dto.SubAccountRequestDto
import com.bank.subAccount.dto.SubAccountRequestUpdate
import com.bank.subAccount.dto.SubAccountResponse
import com.bank.subAccount.entity.SubAccount

interface SubAccountService {

    fun createSubAccount(subAccountRequestDto: SubAccountRequestDto) : SubAccountResponse
    fun getSubAccountDetails(subAccountId : Long) :  SubAccountDetailsResponse
    fun updateSubAccount(subAccountId : Long, subAccountRequestUpdate : SubAccountRequestUpdate) : SubAccountDetailsResponse
    fun deleteSubAccount(subAccountId : Long)
    fun allSubAccountsForMainAccount(accountNumberId : String) : List<SubAccountDetailsResponse>
}