package com.bank.subAccount.dto;

import lombok.Builder;

@Builder
public record SubAccountResponse(

        Long subAccountId
) {
}
