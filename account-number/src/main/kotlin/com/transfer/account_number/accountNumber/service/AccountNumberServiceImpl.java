package com.transfer.account_number.accountNumber.service;

import com.transfer.account_number.accountNumber.dto.request.AccountNumberCreateRequest;
import com.transfer.account_number.accountNumber.entity.AccountNumber;
import com.transfer.account_number.accountNumber.repository.AccountNumberRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;



@Service
@RequiredArgsConstructor
public class AccountNumberServiceImpl implements AccountService{

    private final AccountNumberRepository repository;



    @Value("${account.type.SAVINGS}")
    private String ACCOUNT_TYPE_SAVINGS;
    @Value("${account.type.CHECKING}")
    private String ACCOUNT_TYPE_CHECKING;
    @Value("${account.type.BUSINESS}")
    private String ACCOUNT_TYPE_BUSINESS;
    @Value("${account.type.JOINT}")
    private String ACCOUNT_TYPE_JOINT;


    @Value("${account.branch.code.MAIN}")
    private String BRANCH_MAIN;
    @Value("${account.branch.code.WEST}")
    private String BRANCH_WEST;
    @Value("${account.branch.code.EAST}")
    private String BRANCH_EAST;
    @Value("${account.branch.code.NORTH}")
    private String BRANCH_NORTH;
    @Value("${account.branch.code.SOUTH}")
    private String BRANCH_SOUTH;



    @NotNull
    @Override
    public String generateUniqueAccountNumber(
            @NotNull AccountNumberCreateRequest accountNumberRequest) {

        String accountNumberId;
      do {


           accountNumberId = accountTyp(accountNumberRequest.accountType()) + "-"
                  + branch(accountNumberRequest.branch()) + "-"
                  + generateUUID();


      }while (repository.findByAccountNumber(accountNumberId) != null);

      repository.save(new AccountNumber(accountNumberId, LocalDateTime.now()));


      return accountNumberId;
    }


    private String generateUUID(){
        return UUID.randomUUID().toString();
    }

    private String accountTyp(String accountType){
        return switch (accountType) {
            case "SAVINGS" -> ACCOUNT_TYPE_SAVINGS;
            case "CHECKING" -> ACCOUNT_TYPE_CHECKING;
            case "BUSINESS" -> ACCOUNT_TYPE_BUSINESS;
            default -> ACCOUNT_TYPE_JOINT;
        };
    }
    private String branch(String branch){
        return switch (branch) {
            case "MAIN" -> BRANCH_MAIN;
            case "WEST" -> BRANCH_WEST;
            case "EAST" -> BRANCH_EAST;
            case "NORTH" -> BRANCH_NORTH;
            default -> BRANCH_SOUTH;
        };
    }


    @Override
    public void deleteAccountNumber(@NotNull String accountNumber) {

    }
}
