package com.transfer.account.dto.request;

import com.transfer.account.dto.enums.AccountType;
import com.transfer.account.dto.enums.Branch;
import com.transfer.account.dto.enums.Country;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import lombok.Builder;

@Builder
public record AccountCreateRequest(

         String firstName,
         String lastName,
         String phone,
         @Email
         String email,
         Country country,
         AccountType accountType,
         Branch branch,
         Long userId
) {
}
