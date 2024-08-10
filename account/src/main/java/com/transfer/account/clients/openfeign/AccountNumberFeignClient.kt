package com.transfer.account.clients.openfeign

import com.transfer.account.dto.request.AccountNumberCreateRequest
import jakarta.validation.Valid
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(
    name = "account-number-service",
    url = "\${application.config.account-number-url-feignClient}"
)
interface AccountNumberFeignClient {

    @PostMapping
    fun generateUniqueAccountNumber(
        @Valid @RequestBody accountNumberRequest: AccountNumberCreateRequest
    ): ResponseEntity<String>
}