package com.transfer.account.api;

import com.transfer.account.dto.request.AccountCreateRequest;
import com.transfer.account.dto.request.AccountTransferMoneyRequest;
import com.transfer.account.dto.response.AccountDetailsResponse;
import com.transfer.account.dto.response.AccountResponse;
import com.transfer.account.exceptions.AccountNotFoundException;
import com.transfer.account.kafka.AccountTransactionConfirmation;
import com.transfer.account.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
@Validated
@CrossOrigin()
public class AccountController {

    private final AccountService service;

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(
           @Valid @RequestBody AccountCreateRequest createRequest
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        service.createAccount(createRequest)
                );
    }

    @PostMapping
    public ResponseEntity<String> transferMoney(
            @Valid @RequestBody AccountTransferMoneyRequest request
    ) {
         return ResponseEntity
    }

    @GetMapping("/get/balance")
    public ResponseEntity<BigDecimal> getBalance(
            @RequestParam("account-number") String accountNumber
    ) {
       return ResponseEntity.ok(service.getBalance(accountNumber));
    }

    @GetMapping("/get-details")
    public ResponseEntity<AccountDetailsResponse> getAccountDetails(
            @RequestParam("account-number") String accountNumber
    ) {
        return ResponseEntity.ok(service.getAccountDetails(accountNumber));
    }



}
