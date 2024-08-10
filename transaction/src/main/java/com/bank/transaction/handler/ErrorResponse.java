package com.bank.transaction.handler;

import java.util.Map;

public record ErrorResponse(
        Map<String, Object> errors
) {
}
