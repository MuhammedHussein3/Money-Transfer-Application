package com.transfer.account.mapper;

import com.transfer.account.dto.request.FavoriteAddRequest;
import com.transfer.account.dto.response.FavoriteResponse;
import com.transfer.account.entity.Account;
import com.transfer.account.entity.Favorite;
import org.springframework.stereotype.Service;

@Service
public class FavoriteMapper {
    public Favorite mapToFavorite(FavoriteAddRequest request, Account account) {
        return Favorite.builder()
                .account(account)
                .favoriteAccountNumber(request.favoriteAccountNumber())
                .favoriteNickname(request.favoriteNickName())
                .build();
    }

    public FavoriteResponse mapToFavoriteResponse(Favorite favorite) {

        return FavoriteResponse.builder()
                .favoriteId(favorite.getId())
                .favoriteNickname(favorite.getFavoriteNickname())
                .build();
    }
}
