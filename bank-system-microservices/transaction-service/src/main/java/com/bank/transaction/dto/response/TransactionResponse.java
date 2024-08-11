package com.bank.transaction.dto.response;

import com.bank.transaction.dto.enums.TransactionType;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Builder
public record TransactionResponse(

        UUID transactionID,

        String accountNumberId,

        TransactionType transactionType,

        BigDecimal amount,

        String description,

        Instant transactionDate,

        BigDecimal balanceAfterTransaction
) {
}
