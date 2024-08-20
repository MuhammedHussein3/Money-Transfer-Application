package com.transfer.account.service.impl;

import com.transfer.account.dto.request.AccountCreateRequest;
import com.transfer.account.dto.request.AccountNumberCreateRequest;
import com.transfer.account.dto.request.AccountTransferMoneyRequest;
import com.transfer.account.dto.response.AccountDetailsResponse;
import com.transfer.account.dto.response.AccountResponse;
import com.transfer.account.entity.Account;
import com.transfer.account.exceptions.AccountNotFoundException;
import com.transfer.account.exceptions.EmailAlreadyExistsException;
import com.transfer.account.exceptions.InsufficientBalanceException;
import com.transfer.account.mapper.AccountMapper;
import com.transfer.account.message.AccountCreateNotification;
import com.transfer.account.message.MoneyTransferNotification;
import com.transfer.account.rabbitmq.MainAccountProducer;
import com.transfer.account.repo.AccountRepository;
import com.transfer.account.service.AccountService;
import com.transfer.account.clients.openfeign.AccountNumberFeignClient;
import com.transfer.account.clients.restTemplate.accountNumber.AccountNumberClient;
import com.transfer.account.kafka.AccountProducer;
import com.transfer.account.kafka.AccountTransactionConfirmation;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;
    private final AccountMapper mapper;
    private final AccountNumberClient accountNumberClient;
    private final AccountNumberFeignClient accountNumberFeignClient;
    private final AccountProducer accountTransactionKafkaProducer;
    private final MainAccountProducer mainAccountRabbitMQProducer;
    @Override
    @Transactional(readOnly = true)
    public AccountDetailsResponse getAccountDetails(String accountNumber) {
        Account account = findAccountByAccountNumber(accountNumber);
        return mapToAccountDetailsResponse(account);
    }

    private Account findAccountByAccountNumber(String accountNumber) {
        return repository.getAccountByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException(String.format(
                        "Account not found with AccountId:: %s", accountNumber
                )));
    }

    private AccountDetailsResponse mapToAccountDetailsResponse(Account account) {
        return mapper.mapToAccountDetailsResponse(account);
    }

    @Override
    @Transactional
    public AccountResponse createAccount(
            AccountCreateRequest createRequest
    ) {
        Account account = mapToAccount(createRequest);
        String accountNumberGen = generateAccountNumber(createRequest);
        account.setAccountNumber(accountNumberGen);

        try {
            Account savedAccount = repository.save(account);
            mainAccountRabbitMQProducer.sendAccountCreationMessage(buildAccountCreateNotification(savedAccount));
            return buildAccountResponse(savedAccount);
        } catch (DataIntegrityViolationException e) {
            if (e.getCause() instanceof ConstraintViolationException) {
                throw new EmailAlreadyExistsException("Email " + account.getEmail() + " already exists.");
            }
            throw e;
        }
    }

    private Account mapToAccount(AccountCreateRequest createRequest) {
        return mapper.mapToAccount(createRequest);
    }

    private String generateAccountNumber(AccountCreateRequest createRequest) {
        AccountNumberCreateRequest accountNumberCreateRequest = buildAccountNumberRequest(createRequest);
        return accountNumberFeignClient.generateUniqueAccountNumber(accountNumberCreateRequest).getBody();
    }

    private AccountNumberCreateRequest buildAccountNumberRequest(AccountCreateRequest createRequest) {
        return AccountNumberCreateRequest.builder()
                .accountType(createRequest.accountType().toString())
                .branch(createRequest.branch().toString())
                .build();
    }

    private AccountResponse buildAccountResponse(Account savedAccount) {
        return AccountResponse.builder()
                .accountNumber(savedAccount.getAccountNumber())
                .description("Account Created Successfully")
                .build();
    }

    private AccountCreateNotification buildAccountCreateNotification(
            Account newAccount
    ){
        return AccountCreateNotification.builder()
                .email(newAccount.getEmail())
                .balance(newAccount.getBalance())
                .phone(newAccount.getPhone())
                .firstName(newAccount.getFirstName())
                .lastName(newAccount.getLastName())
                .accountType(newAccount.getAccountType().toString())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal getBalance(String accountNumber) {
        return repository.getAccountCurrentBalanceByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException(String.format(
                        "Account Not Found With AccountNumber:: %s", accountNumber
                )));
    }


    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void transferMoney(AccountTransferMoneyRequest request) {

        Account source = findAccountByNumber(request.fromAccountNumber(), "Account not found with AccountNumber: %s");
        Account destination = findAccountByNumber(request.toAccountNumber(), "Recipient Account not found with AccountNumber: %s");

        Account[] accounts = new Account[]{source, destination};
        Arrays.sort(accounts, (first, second)-> first.getId().compareTo(second.getId()));

        synchronized (accounts[0]){
            synchronized (accounts[1]){
                validateSufficientBalance(source, request.amount());

                processTransfer(source, destination, request.amount());

                sendTransactionConfirmation(source, request);

                sendMoneyTransferNotification(buildMoneyTransferNotification(source, destination, request.amount()));
            }
        }

    }


    private void sendMoneyTransferNotification(MoneyTransferNotification moneyTransferNotification){
        mainAccountRabbitMQProducer.sendMoneyTransferMessage(moneyTransferNotification);
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

        accountTransactionKafkaProducer.sendAccountConfirmationToTransaction(confirmation);
    }

    private MoneyTransferNotification buildMoneyTransferNotification(Account fromAccount, Account toAccount, BigDecimal amount){
        return MoneyTransferNotification
                .builder()
                .email(fromAccount.getEmail())
                .userId(fromAccount.getUserId())
                .fromAccountNumberId(fromAccount.getAccountNumber())
                .toAccountNumberId(toAccount.getAccountNumber())
                .amount(amount)
                .balanceAfterTransaction(fromAccount.getBalance())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public Account getAccountByAccountNumber(String accountNumber){
        return repository.getAccountByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException(String.format("Account Not Found With AccountNumber:: %s", accountNumber)));
    }
}
