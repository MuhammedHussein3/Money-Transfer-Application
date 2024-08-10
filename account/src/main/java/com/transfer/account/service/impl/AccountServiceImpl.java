package com.transfer.account.service.impl;

import com.transfer.account.dto.request.AccountCreateRequest;
import com.transfer.account.dto.request.AccountNumberCreateRequest;
import com.transfer.account.dto.request.AccountTransferMoneyRequest;
import com.transfer.account.dto.response.AccountDetailsResponse;
import com.transfer.account.dto.response.AccountResponse;
import com.transfer.account.entity.Account;
import com.transfer.account.exceptions.AccountNotFoundException;
import com.transfer.account.exceptions.InsufficientBalanceException;
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

        Account fromAccount = findAccountByNumber(request.fromAccountNumber(), "Account not found with AccountNumber: %s");
        Account toAccount = findAccountByNumber(request.toAccountNumber(), "Recipient Account not found with AccountNumber: %s");

        validateSufficientBalance(fromAccount, request.amount());

        processTransfer(fromAccount, toAccount, request.amount());

        sendTransactionConfirmation(fromAccount, request);
    }

    private Account findAccountByNumber(String accountNumber, String errorMessage) {
        return repository.findAccountByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException(String.format(errorMessage, accountNumber)));
    }

    private void validateSufficientBalance(Account account, BigDecimal amount) {
        if (account.getBalance().compareTo(amount) < 0) {
            throw new InsufficientBalanceException("Insufficient balance for the transaction.");
        }
    }

    private void processTransfer(Account fromAccount, Account toAccount, BigDecimal amount) {
        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));

        repository.save(fromAccount);
        repository.save(toAccount);
    }

    private void sendTransactionConfirmation(Account fromAccount, AccountTransferMoneyRequest request) {
        AccountTransactionConfirmation confirmation = AccountTransactionConfirmation.builder()
                .accountNumberId(fromAccount.getAccountNumber())
                .amount(request.amount())
                .transactionType(request.transactionType())
                .balanceAfterTransaction(fromAccount.getBalance())
                .description("Transaction processed successfully")
                .build();

        accountTransactionProducer.sendAccountConfirmationToTransaction(confirmation);
    }

    @Override
    public Account getAccountByAccountNumber(String accountNumber){
        return repository.getAccountByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException(String.format("Account Not Found With AccountNumber:: %s", accountNumber)));
    }
}
