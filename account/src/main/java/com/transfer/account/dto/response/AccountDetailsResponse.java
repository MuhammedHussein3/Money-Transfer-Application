package com.transfer.account.dto.response;

import lombok.Builder;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record AccountDetailsResponse (
        String accountNumber,
        String country,
        String accountType,
        String branch,
        Instant createdAt

){
}
