package com.transfer.account.service;

import com.transfer.account.dto.request.AccountCreateRequest;
import com.transfer.account.dto.request.AccountTransferMoneyRequest;
import com.transfer.account.dto.response.AccountDetailsResponse;
import com.transfer.account.dto.response.AccountResponse;
import com.transfer.account.entity.Account;

import java.math.BigDecimal;

public interface AccountService {


    AccountDetailsResponse getAccountDetails(String accountNumber);

    AccountResponse createAccount(AccountCreateRequest createRequest);

    BigDecimal getBalance(String accountNumber);

    void transferMoney(AccountTransferMoneyRequest request);

    Account getAccountByAccountNumber(String accountNumber);
}
