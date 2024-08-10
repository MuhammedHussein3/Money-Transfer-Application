package com.bank.transaction.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record TransactionPageResponse(

        List<TransactionResponse> transactionsHistoryForThisAccount,

        Integer pageNumber,

        Integer pageSize,

        int totalElement,

        int totalPages,

        boolean isLast
) {
}
