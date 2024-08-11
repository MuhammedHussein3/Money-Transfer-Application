package com.transfer.account.dto.request;

import lombok.Builder;

@Builder
public record FavoriteAddRequest(
        String accountNumber,

        String favoriteAccountNumber,

        String favoriteNickName
) {
}
