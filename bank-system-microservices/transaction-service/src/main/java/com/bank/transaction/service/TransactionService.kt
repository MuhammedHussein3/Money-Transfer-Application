package com.bank.transaction.service

import com.bank.transaction.dto.enums.TransactionType
import com.bank.transaction.dto.response.TransactionPageResponse
import com.bank.transaction.dto.response.TransactionResponse
import com.bank.transaction.kafka.AccountTransactionConfirmation

interface TransactionService {

    fun createTransaction(
        transactionCreateRequest: AccountTransactionConfirmation
    ): String

    fun getTransactionsByAccountNumberId(
        accountNumberId : String
    ) : List<TransactionResponse>

    fun getTransactionsByAccountNumberIdAndTransactionType(
        accountNumberId : String,
        transactionType : TransactionType,
        pageNo : Int,
        pageSize : Int,
        sortBy : String,
        sortDir : String
    ): TransactionPageResponse
}