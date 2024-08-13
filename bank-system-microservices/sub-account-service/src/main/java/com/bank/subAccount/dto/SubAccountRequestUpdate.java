package com.bank.subAccount.dto;

import java.math.BigDecimal;

public record SubAccountRequestUpdate(
        String SubAccountName,

        BigDecimal balance
) {
}
