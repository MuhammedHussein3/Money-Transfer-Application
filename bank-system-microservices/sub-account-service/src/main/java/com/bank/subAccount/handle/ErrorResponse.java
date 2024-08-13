package com.bank.subAccount.handle;

import java.util.Map;

public record ErrorResponse(
        Map<String, Object> errors
) {
}
