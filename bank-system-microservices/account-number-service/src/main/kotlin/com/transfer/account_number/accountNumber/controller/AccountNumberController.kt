package com.transfer.account_number.accountNumber.controller

import com.transfer.account_number.accountNumber.dto.request.AccountNumberCreateRequest
import com.transfer.account_number.accountNumber.service.AccountService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/accountNumbers")
@Validated
class AccountNumberController(private val service: AccountService) {

    @PostMapping
    fun generateUniqueAccountNumber(
        @Valid @RequestBody accountNumberRequest: AccountNumberCreateRequest
    ): ResponseEntity<String> {
        val accountNumber = service.generateUniqueAccountNumber(accountNumberRequest)
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(accountNumber)
    }
}