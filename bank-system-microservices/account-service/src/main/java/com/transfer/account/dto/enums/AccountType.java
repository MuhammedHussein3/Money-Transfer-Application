package com.transfer.account.dto.enums;

import lombok.Getter;

@Getter
public enum AccountType {
    SAVINGS("Savings Account"),
    CHECKING("Checking Account"),
    BUSINESS("Business Account"),
    JOINT("Joint Account");

    private final String description;

    AccountType(String description) {
        this.description = description;
    }
}
