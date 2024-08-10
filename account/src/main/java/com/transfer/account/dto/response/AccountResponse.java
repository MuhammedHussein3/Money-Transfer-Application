package com.transfer.account.dto.response;

import lombok.Builder;

@Builder
public record AccountResponse(
        String accountNumber,
        String description
) {
}
