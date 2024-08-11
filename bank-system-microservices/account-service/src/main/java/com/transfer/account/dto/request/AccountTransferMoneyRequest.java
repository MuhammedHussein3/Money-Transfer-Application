package com.transfer.account.dto.request;

import com.transfer.account.kafka.TransactionType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;


import java.math.BigDecimal;

@Builder
public record AccountTransferMoneyRequest(

        @NotNull(message = "To account number must not be null")
//        @Size(min = 14, message = "To account number must not be empty")
        String fromAccountNumber,

        @NotNull(message = "To account number must not be null")
        @Size(min = 14, message = "To account number must not be empty")
        String toAccountNumber,

        @NotNull(message = "Amount must not be null")
        @Min(value = 0, message = "Amount must be greater than or equal to zero")
        BigDecimal amount,

        @NotNull(message = "TransactionType must be required")
        TransactionType transactionType
) {}
