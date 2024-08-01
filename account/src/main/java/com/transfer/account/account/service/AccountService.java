package com.transfer.account.account.service;

import com.transfer.account.account.dto.request.AccountCreateRequest;

public interface AccountService {

    void createAccount(AccountCreateRequest createRequest);
}
