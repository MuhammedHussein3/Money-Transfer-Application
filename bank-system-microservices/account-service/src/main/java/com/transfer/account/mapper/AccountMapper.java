package com.transfer.account.mapper;

import com.transfer.account.dto.request.AccountCreateRequest;
import com.transfer.account.dto.response.AccountDetailsResponse;
import com.transfer.account.entity.Account;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;

@Service
public class AccountMapper {


    public Account mapToAccount(AccountCreateRequest createRequest) {

        return Account.builder()
                .firstName(createRequest.firstName())
                .lastName(createRequest.lastName())
                .email(createRequest.email())
                .phone(createRequest.phone())
                .accountType(createRequest.accountType())
                .branch(createRequest.branch())
                .userId(createRequest.userId())
                .country(createRequest.country())
                .balance(BigDecimal.valueOf(0))
                .build();
    }

    public AccountDetailsResponse mapToAccountDetailsResponse(Account account) {
        return AccountDetailsResponse.builder()
                .firstName(account.getFirstName())
                .lastName(account.getLastName())
                .email(account.getEmail())
                .phone(account.getPhone())
                .accountType(account.getAccountType().toString())
                .accountNumber(account.getAccountNumber())
                .createdAt(Instant.now())
                .branch(account.getBranch().toString())
                .country(account.getCountry().toString())
                .build();

    }
}
