package com.bank.transaction.service.impl;

import com.bank.transaction.dto.enums.TransactionType;
import com.bank.transaction.dto.response.TransactionPageResponse;
import com.bank.transaction.dto.response.TransactionResponse;
import com.bank.transaction.entity.TransactionHistoryArchive;
import com.bank.transaction.kafka.AccountTransactionConfirmation;
import com.bank.transaction.mappers.TransactionMapper;
import com.bank.transaction.repo.TransactionHistoryArchiveRepo;
import com.bank.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private final TransactionHistoryArchiveRepo repo;
    private final TransactionMapper mapper;


    @NotNull
    @Override
    public String createTransaction(
            @NotNull AccountTransactionConfirmation createRequest
    ) {
       return repo.save(mapper.mapToTransaction(createRequest)).
               getTransactionId().
               toString();
    }


    @NotNull
    @Override
    public List<TransactionResponse> getTransactionsByAccountNumberId(
            @NotNull String accountNumberId
    ) {
        return repo.findByAccountNumberIdOrderByTimestampDesc(accountNumberId)
                .stream()
                .map(mapper::mapToTransactionResponse)
                .toList();
    }

    @NotNull
    @Override
    public TransactionPageResponse getTransactionsByAccountNumberIdAndTransactionType(
            @NotNull String accountNumberId,
            @NotNull TransactionType transactionType,
            int pageNo,
            int pageSize,
            @NotNull String sortBy,
            @NotNull String sortDir
    ) {

        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<TransactionHistoryArchive> transactionPage =  repo.findByAccountNumberIdAndTransactionType(
                accountNumberId,
                transactionType,
                pageable
        );
        return TransactionPageResponse.builder()
                .totalElement(transactionPage.getNumberOfElements())
                .totalPages(transactionPage.getTotalPages())
                .pageNumber(pageNo)
                .pageSize(pageSize)
                .transactionsHistoryForThisAccount(
                        transactionPage.getContent().
                                stream()
                                .map(mapper::mapToTransactionResponse).toList()
                )
                .build();
    }
}
