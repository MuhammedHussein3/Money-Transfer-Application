package com.bank.subAccount.service.impl;

import com.bank.subAccount.dto.SubAccountRequestDto;
import com.bank.subAccount.dto.SubAccountResponse;
import com.bank.subAccount.entity.SubAccount;
import com.bank.subAccount.mapper.SubAccountMapper;
import com.bank.subAccount.repo.SubAccountRepository;
import com.bank.subAccount.service.SubAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubAccountServiceImpl implements SubAccountService {

    private final SubAccountRepository repository;
    private final SubAccountMapper mapper;

    @NotNull
    @Override
    public SubAccountResponse createSubAccount(
            @NotNull SubAccountRequestDto subAccountRequestDto
    ) {
        SubAccount savedSubAccount = repository.save(mapToSubAccount(subAccountRequestDto));
        return mapToSubAccountResponse(savedSubAccount);
    }
    private SubAccount mapToSubAccount(SubAccountRequestDto requestDto){
        return mapper.mapToSubAccount(requestDto);
    }

    private SubAccountResponse mapToSubAccountResponse(SubAccount subAccount){
        return mapper.mapToSubAccountResponse(subAccount);
    }
}
