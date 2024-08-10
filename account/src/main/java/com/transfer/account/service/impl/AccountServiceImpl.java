package com.transfer.account.service.impl;

import com.transfer.account.dto.request.AccountCreateRequest;
import com.transfer.account.dto.request.AccountNumberCreateRequest;
import com.transfer.account.dto.request.AccountTransferMoneyRequest;
import com.transfer.account.dto.response.AccountDetailsResponse;
import com.transfer.account.dto.response.AccountResponse;
import com.transfer.account.entity.Account;
import com.transfer.account.exceptions.AccountNotFoundException;
import com.transfer.account.mapper.AccountMapper;
import com.transfer.account.repo.AccountRepository;
import com.transfer.account.service.AccountService;
import com.transfer.account.clients.openfeign.AccountNumberFeignClient;
import com.transfer.account.clients.restTemplate.accountNumber.AccountNumberClient;
import com.transfer.account.kafka.AccountProducer;
import com.transfer.account.kafka.AccountTransactionConfirmation;
import com.transfer.account.kafka.TransactionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;
    private final AccountMapper mapper;
    private final AccountNumberClient accountNumberClient;
    private final AccountNumberFeignClient accountNumberFeignClient;
    private final AccountProducer accountTransactionProducer;

    @Override
    public AccountDetailsResponse getAccountDetails(String accountNumber) {
        return repository.getAccountByAccountNumber(accountNumber)
                .map(mapper::mapToAccountDetailsResponse)
                .orElseThrow(() -> new AccountNotFoundException(String.format(
                        "Account not found with AccountId:: %s", accountNumber
                )));
    }

    @Override
    public AccountResponse createAccount(AccountCreateRequest createRequest) {
        // Map from AccountCreateRequest to Account
        var account = mapper.mapToAccount(createRequest);

        // Generate Account Number In AccountNumber Service
        AccountNumberCreateRequest accountNumberCreateRequest = AccountNumberCreateRequest
                .builder()
                        .accountType(createRequest.accountType().toString())
                                .branch(createRequest.branch().toString())
                                        .build();
        String accountNumberGen = accountNumberFeignClient.generateUniqueAccountNumber(accountNumberCreateRequest).getBody();
        account.setAccountNumber(accountNumberGen);

        var savedAccount = repository.save(account);
        return AccountResponse.builder()
                .accountNumber(savedAccount.getAccountNumber())
                .description("Account Created Successfully")
                .build();
    }

    @Override
    public BigDecimal getBalance(String accountNumber) {
        return repository.getAccountCurrentBalanceByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException(String.format(
                        "Account Not Found With AccountNumber:: %s", accountNumber
                )));
    }



    @Override
    public void transferMoney(AccountTransferMoneyRequest request) {

        var fromAccount = repository.findAccountByAccountNumber(request.fromAccountNumber())
                .orElseThrow(() -> new AccountNotFoundException(String.format("Account not found with AccountNumber:: %s", request.fromAccountNumber())));

        var toAccount = repository.findAccountByAccountNumber(request.toAccountNumber())
                .orElseThrow(() -> new AccountNotFoundException(String.format("Recipient Account not found with AccountNumber:: %s", request.toAccountNumber())));


        fromAccount.setBalance(fromAccount.getBalance().subtract(request.amount()));
        repository.save(fromAccount);
        toAccount.setBalance(fromAccount.getBalance().add(request.amount()));
        repository.save(toAccount);


        AccountTransactionConfirmation accountTransactionConfirmation =
                AccountTransactionConfirmation.builder()
                        .accountNumberId(fromAccount.getAccountNumber())
                        .amount(request.amount())
                        .transactionType(request.transactionType())
                        .balanceAfterTransaction(BigDecimal.valueOf(fromAccount.getBalance().longValue()))
                        .description("Hello Kafka")
                        .build();

        accountTransactionProducer.sendAccountConfirmationToTransaction(accountTransactionConfirmation);
    }

    @Override
    public Account getAccountByAccountNumber(String accountNumber){
        return repository.getAccountByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException(String.format("Account Not Found With AccountNumber:: %s", accountNumber)));
    }
}
