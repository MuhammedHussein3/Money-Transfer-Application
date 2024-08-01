package com.transfer.account.account.dto.request;

import com.transfer.account.account.dto.enums.AccountType;
import com.transfer.account.account.dto.enums.Branch;
import com.transfer.account.account.dto.enums.Country;
import lombok.Builder;

@Builder
public record AccountCreateRequest(
        String userName,

        Country country,

        AccountType accountType,

        Branch branch,

        String password,

        Long userId
) {
}
