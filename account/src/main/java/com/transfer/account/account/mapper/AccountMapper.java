package com.transfer.account.account.mapper;

import com.transfer.account.account.dto.request.AccountCreateRequest;
import com.transfer.account.account.dto.response.AccountDetailsResponse;
import com.transfer.account.account.entity.Account;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountMapper {


    public Account mapToAccount(AccountCreateRequest createRequest) {

        return Account.builder()
                .accountType(createRequest.accountType())
                .email(createRequest.email())
                .branch(createRequest.branch())
                .userId(createRequest.userId())
                .userName(createRequest.userName())
                .country(createRequest.country())
                .balance(BigDecimal.valueOf(0))
                .build();
    }

    public AccountDetailsResponse mapToAccountDetailsResponse(Account account) {
        return AccountDetailsResponse.builder()
                .accountType(account.getAccountType().toString())
                .userName(account.getUserName())
                .accountNumber(account.getAccountNumber())
                .email(account.getEmail())
                .createdAt(account.getCreatedAt())
                .branch(account.getBranch().toString())
                .country(account.getCountry().toString())
                .dateOfBirth(account.getDateOfrBirth())
                .build();

    }
}
