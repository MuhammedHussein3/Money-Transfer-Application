package com.transfer.account_number.accountNumber.service

import com.transfer.account_number.accountNumber.dto.request.AccountNumberCreateRequest

interface AccountService {

    fun generateUniqueAccountNumber(accountNumberRequest: AccountNumberCreateRequest): String
    fun deleteAccountNumber(accountNumber: String)
}