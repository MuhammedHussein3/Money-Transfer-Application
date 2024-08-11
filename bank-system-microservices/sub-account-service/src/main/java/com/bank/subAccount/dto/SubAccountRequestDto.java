package com.bank.subAccount.dto;

import com.bank.subAccount.dto.enums.SubAccountType;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record SubAccountRequestDto(
         String accountNumberId,
         String subAccountName,
         SubAccountType subAccountType,
         BigDecimal balance
) {
}
