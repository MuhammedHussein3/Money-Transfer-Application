package com.transfer.account.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record AccountNumberCreateRequest(

        @NotBlank(message = "AccountType must be required")
        String accountType,

        @NotBlank(message = "BranchCode must be required")
        String branch
) {
}