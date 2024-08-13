package com.notification.message;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class MoneyTransferNotification{
    String email;
    LocalDateTime timestamp;
    String fromAccountId;
    String toAccountId;
    Long userId;
    BigDecimal amount;
    BigDecimal balanceAfterTransaction;
}
