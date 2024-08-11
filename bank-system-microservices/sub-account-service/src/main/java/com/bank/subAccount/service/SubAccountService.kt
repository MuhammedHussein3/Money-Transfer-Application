package com.bank.subAccount.service

import com.bank.subAccount.dto.SubAccountRequestDto
import com.bank.subAccount.dto.SubAccountResponse

interface SubAccountService {

    fun createSubAccount(subAccountRequestDto: SubAccountRequestDto) : SubAccountResponse
}