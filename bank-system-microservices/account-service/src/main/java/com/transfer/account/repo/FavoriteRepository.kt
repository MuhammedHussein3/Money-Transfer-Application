package com.transfer.account.repo

import com.transfer.account.entity.Favorite
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface FavoriteRepository : JpaRepository<Favorite, Int> {


    @EntityGraph(value = "Favorite.account", type = EntityGraph.EntityGraphType.LOAD)
    @Query("""
        SELECT f 
        FROM Favorite f
        WHERE f.account.accountNumber like :accountNumber
    """)
    fun getAllFavoriteByAccountNumber(accountNumber : String) : List<Favorite>
}