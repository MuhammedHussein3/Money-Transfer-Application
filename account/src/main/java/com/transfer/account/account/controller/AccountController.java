package com.transfer.account.account.controller;

import com.transfer.account.account.dto.request.AccountCreateRequest;
import com.transfer.account.account.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
@Validated
@CrossOrigin
public class AccountController {

    private final AccountService service;

    @PostMapping
    public ResponseEntity<String> createAccount(
           @Valid @RequestBody AccountCreateRequest createRequest
    ) {
        service.createAccount(createRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        "Account Created Successfully"
                );
    }

    @GetMapping("/get/balance")
    public ResponseEntity<BigDecimal> getBalance(
            @RequestParam("account-number") String accountNumber
    ) {
       return ResponseEntity.ok(service.getBalance(accountNumber));
    }
}
