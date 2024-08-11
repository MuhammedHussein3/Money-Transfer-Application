package com.transfer.account.dto.response;

import lombok.Builder;

@Builder
public record FavoriteResponse(
        Integer favoriteId,
        String favoriteNickname
) {
}
