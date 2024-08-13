package com.transfer.account.handle;

import java.util.Map;

public record ErrorResponse(
        Map<String, Object> errors
) {
}
