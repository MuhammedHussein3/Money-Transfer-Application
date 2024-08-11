package com.transfer.account_number.accountNumber.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AccountNumberCreateRequest(

        @NotBlank(message = "AccountType must be required")
        String accountType,

        @NotBlank(message = "BranchCode must be required")
        String branch
) {
}
