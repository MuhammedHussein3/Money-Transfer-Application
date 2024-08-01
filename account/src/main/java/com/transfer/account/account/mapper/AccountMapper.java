package com.transfer.account.account.mapper;

import com.transfer.account.account.dto.request.AccountCreateRequest;
import com.transfer.account.account.entity.Account;
import org.springframework.stereotype.Service;

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
                .build();
    }
}
