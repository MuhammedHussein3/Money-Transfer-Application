package com.bank.transaction.kafka;

import com.bank.transaction.dto.enums.TransactionType;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record AccountTransactionConfirmation(
        @NotNull(message = "Account number cannot be null")
        @NotEmpty(message = "Account number cannot be empty")
        String accountNumberId,

        @NotNull(message = "Transaction type cannot be null")
        TransactionType transactionType,

        @NotNull(message = "Amount cannot be null")
        @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
        @Digits(integer = 10, fraction = 2, message = "Amount should be a valid decimal number with up to 10 digits and 2 decimal places")
        BigDecimal amount,

        @Size(max = 255, message = "Description cannot exceed 255 characters")
        String description,

        @NotNull(message = "Balance after transaction cannot be null")
        @DecimalMin(value = "0.01", message = "Balance after transaction must be greater than 0")
        @Digits(integer = 10, fraction = 2, message = "Balance after transaction should be a valid decimal number with up to 10 digits and 2 decimal places")
        BigDecimal balanceAfterTransaction

) {
}
