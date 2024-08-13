package com.transfer.account.message;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class MoneyTransferNotification{

    String email;
    LocalDateTime timestamp;
    String fromAccountNumberId;
    String toAccountNumberId;
    Long userId;
    BigDecimal amount;
    BigDecimal balanceAfterTransaction;
}
