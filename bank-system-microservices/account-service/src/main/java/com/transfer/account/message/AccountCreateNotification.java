package com.transfer.account.message;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class AccountCreateNotification {
    private String firstName;
    private String lastName;
    private String accountType;
    private String email;
    private BigDecimal balance;
    private String phone;
}
