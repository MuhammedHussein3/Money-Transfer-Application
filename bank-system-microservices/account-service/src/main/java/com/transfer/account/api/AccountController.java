package com.transfer.account.api;

import com.transfer.account.dto.request.AccountCreateRequest;
import com.transfer.account.dto.request.AccountTransferMoneyRequest;
import com.transfer.account.dto.response.AccountDetailsResponse;
import com.transfer.account.dto.response.AccountResponse;
import com.transfer.account.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@SecurityRequirement(name = "bearerAuth")
@CrossOrigin()
public class AccountController {

    private final AccountService service;


    @Operation(
            summary = "Create a new account",
            description = "Creates a new account with the provided details.",
            tags = {"accounts"},
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Account created successfully",
                            content = @Content(schema = @Schema(implementation = AccountResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid request data"
                    )
            }
    )
    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(
            @Valid @RequestBody AccountCreateRequest createRequest
    ) {
        AccountResponse response = service.createAccount(createRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Transfer money between accounts",
            description = "Transfers the specified amount from one account to another.",
            tags = {"accounts"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Transfer completed successfully"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid request data"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Account not found"
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Insufficient balance"
                    )
            }
    )
    @PostMapping("/transfer")
    public ResponseEntity<String> transferMoney(
            @Valid @RequestBody AccountTransferMoneyRequest request
    ) {
        service.transferMoney(request);
        return ResponseEntity.ok("Transfer operation successfully completed.");
    }


    @Operation(
            summary = "Get account balance",
            description = "Retrieves the current balance of the specified account.",
            tags = {"accounts"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Balance retrieved successfully",
                            content = @Content(schema = @Schema(implementation = BigDecimal.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Account not found"
                    )
            }
    )
    @GetMapping("/balance")
    public ResponseEntity<BigDecimal> getBalance(
            @RequestParam("account-number") String accountNumber
    ) {
        BigDecimal balance = service.getBalance(accountNumber);
        return ResponseEntity.ok(balance);
    }


    @Operation(
            summary = "Get account details",
            description = "Retrieves detailed information about the specified account.",
            tags = {"accounts"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Account details retrieved successfully",
                            content = @Content(schema = @Schema(implementation = AccountDetailsResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Account not found")
            }
    )
    @GetMapping("/details")
    public ResponseEntity<AccountDetailsResponse> getAccountDetails(
            @RequestParam("account-number") String accountNumber
    ) {
        AccountDetailsResponse details = service.getAccountDetails(accountNumber);
        return ResponseEntity.ok(details);
    }
}