package com.transfer.account.service.impl;

import com.transfer.account.dto.request.FavoriteAddRequest;
import com.transfer.account.dto.response.FavoriteResponse;
import com.transfer.account.exceptions.FavoriteNotFoundException;
import com.transfer.account.mapper.FavoriteMapper;
import com.transfer.account.repo.FavoriteRepository;
import com.transfer.account.service.AccountService;
import com.transfer.account.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteMapper favoriteMapper;
    private final FavoriteRepository favoriteRepository;
    private final AccountService accountService;



    @NotNull
    @Override
    public FavoriteResponse addFavorite(@NotNull FavoriteAddRequest request) {

        var account = accountService.getAccountByAccountNumber(request.accountNumber());

        var favorite = favoriteMapper.mapToFavorite(request, account);

        favoriteRepository.save(favorite);

        return favoriteMapper.mapToFavoriteResponse(favorite);
    }

    @Override
    public void removeFavorite(int favoriteId) {

        isFindFavoriteByFavoriteId(favoriteId);

        favoriteRepository.deleteById(favoriteId);
    }

    private void isFindFavoriteByFavoriteId(int favoriteId){
        favoriteRepository.findById(favoriteId)
                .orElseThrow(() -> new FavoriteNotFoundException(String.format(
                        "Favorite not found with favoriteId:: %d", favoriteId
                )));
    }

    @NotNull
    @Override
    public List<FavoriteResponse> getAllFavorites(@NotNull String accountNumber) {
        return favoriteRepository.getAllFavoriteByAccountNumber(accountNumber)
                .stream()
                .map(favoriteMapper::mapToFavoriteResponse)
                .toList();
    }
}
