package com.bank.subAccount.api;

import com.bank.subAccount.dto.SubAccountRequestDto;
import com.bank.subAccount.dto.SubAccountResponse;
import com.bank.subAccount.service.SubAccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sub-accounts")
@RequiredArgsConstructor
public class SubAccountController {

    private final SubAccountService service;


    @PostMapping
    public ResponseEntity<SubAccountResponse> createSubAccount(
            @Valid @RequestBody SubAccountRequestDto requestDto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.createSubAccount(requestDto));
    }
}
