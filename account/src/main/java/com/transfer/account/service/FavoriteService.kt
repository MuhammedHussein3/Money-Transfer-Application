package com.transfer.account.service

import com.transfer.account.dto.request.FavoriteAddRequest
import com.transfer.account.dto.response.FavoriteResponse

interface FavoriteService{

    fun addFavorite(favoriteAddRequest: FavoriteAddRequest) : FavoriteResponse

    fun removeFavorite(favoriteId : Int)

    fun getAllFavorites(accountNumber : String) : List<FavoriteResponse>
}