package com.bank.transaction.mappers;


import com.bank.transaction.dto.response.TransactionResponse;
import com.bank.transaction.entity.TransactionHistoryArchive;
import com.bank.transaction.kafka.AccountTransactionConfirmation;
import org.springframework.stereotype.Service;

@Service
public class TransactionMapper {
    public TransactionHistoryArchive mapToTransaction(AccountTransactionConfirmation request){
        return TransactionHistoryArchive.builder()
                .accountNumberId(request.accountNumberId())
                .transactionType(request.transactionType())
                .description(request.description())
                .amount(request.amount())
                .balanceAfterTransaction(request.balanceAfterTransaction())
                .build();
    }

    public TransactionResponse mapToTransactionResponse(TransactionHistoryArchive request){
        return TransactionResponse.builder()
                .transactionType(request.getTransactionType())
                .accountNumberId(request.getAccountNumberId())
                .transactionID(request.getTransactionId())
                .description(request.getDescription())
                .transactionDate(request.getTimestamp())
                .amount(request.getAmount())
                .balanceAfterTransaction(request.getBalanceAfterTransaction())
                .build();
    }
}
