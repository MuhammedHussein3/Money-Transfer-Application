package com.transfer.account.dto.request;

import com.transfer.account.dto.enums.AccountType;
import com.transfer.account.dto.enums.Branch;
import com.transfer.account.dto.enums.Country;
import lombok.Builder;

@Builder
public record AccountCreateRequest(

        Country country,

        AccountType accountType,

        Branch branch,

        Long userId
) {
}
