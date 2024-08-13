package com.bank.transaction.api;

import com.bank.transaction.dto.enums.TransactionType;
import com.bank.transaction.dto.response.TransactionPageResponse;
import com.bank.transaction.dto.response.TransactionResponse;
import com.bank.transaction.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
@Validated
@SecurityRequirement(name = "bearerAuth")
public class TransactionController {

    private final TransactionService service;


    @Operation(
            summary = "Get transactions by account number",
            description = "Retrieve all transactions associated with a specific account number"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transactions retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Account not found")
    })
    @GetMapping
    public ResponseEntity<List<TransactionResponse>> getTransactionsByAccountNumber(
            @RequestParam("account-number-id") String accountNumberId
    ) {
        return ResponseEntity.ok(service.getTransactionsByAccountNumberId(accountNumberId));
    }


    @Operation(
            summary = "Get transactions by account number and type",
            description = "Retrieve transactions filtered by account number and transaction type"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Filtered transactions retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Account not found")
    })

    @GetMapping("/filter")
    public ResponseEntity<TransactionPageResponse> getTransactionsByAccountNumberAndType(
            @RequestParam("account-number-id") String accountNumberId,
            @RequestParam("transaction-type") TransactionType transactionType,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "timestamp") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir
    ) {
        return ResponseEntity.ok(service.getTransactionsByAccountNumberIdAndTransactionType(
                accountNumberId, transactionType, pageNo, pageSize, sortBy, sortDir
        ));
    }
}
