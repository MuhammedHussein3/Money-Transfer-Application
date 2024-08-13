package com.bank.subAccount.service.impl;

import com.bank.subAccount.dto.SubAccountDetailsResponse;
import com.bank.subAccount.dto.SubAccountRequestDto;
import com.bank.subAccount.dto.SubAccountRequestUpdate;
import com.bank.subAccount.dto.SubAccountResponse;
import com.bank.subAccount.entity.SubAccount;
import com.bank.subAccount.exceptions.SubAccountNotFoundException;
import com.bank.subAccount.mapper.SubAccountMapper;
import com.bank.subAccount.repo.SubAccountRepository;
import com.bank.subAccount.service.SubAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


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

    @NotNull
    @Override
    public SubAccountDetailsResponse getSubAccountDetails(long subAccountId) {
        return mapToSubAccountDetails(findSubAccountsById(subAccountId));
    }
    private SubAccount findSubAccountsById(Long subAccountId){
        isSubAccountExist(subAccountId);
        return repository.findById(subAccountId)
                .orElseThrow(() ->
                        new SubAccountNotFoundException(String.format("SubAccount not found with ID %d", subAccountId))
                );
    }
    private SubAccountDetailsResponse mapToSubAccountDetails(SubAccount subAccount){
        return mapper.mapToSubAccountDetails(subAccount);
    }
    private SubAccount isSubAccountExist(Long subAccountId){
       return repository.findById(subAccountId)
                .orElseThrow(() ->
                        new SubAccountNotFoundException(String.format("SubAccount not found with ID %d", subAccountId))
                );
    }

    @NotNull
    @Override
    public SubAccountDetailsResponse updateSubAccount(
            long subAccountId, @NotNull SubAccountRequestUpdate subAccountRequestUpdate
    ) {
       var curSubAccount = isSubAccountExist(subAccountId);

        merging(curSubAccount, subAccountRequestUpdate);

        repository.save(curSubAccount);

        return mapper.mapToSubAccountDetails(curSubAccount);
    }

    private void merging(
            SubAccount curSubAccount, SubAccountRequestUpdate subAccountRequestUpdate
    ) {

        if (StringUtils.isNotBlank(subAccountRequestUpdate.SubAccountName()))
            curSubAccount.setSubAccountName(subAccountRequestUpdate.SubAccountName());
        if (subAccountRequestUpdate.balance() != null)
            curSubAccount.setBalance(subAccountRequestUpdate.balance());
    }

    @Override
    public void deleteSubAccount(long subAccountId) {
        var subAccount = isSubAccountExist(subAccountId);
        repository.delete(subAccount);
    }

    @NotNull
    @Override
    public List<SubAccountDetailsResponse> allSubAccountsForMainAccount(@NotNull String accountNumberId) {
        return getSubAccountDetailsForMainAccount(getAllSubAccountsForMainAccount(accountNumberId));
    }
    private List<SubAccount> getAllSubAccountsForMainAccount(String accountNumberId){
        return repository.getSubAccountsForMainAccount(accountNumberId);
    }
    private List<SubAccountDetailsResponse> getSubAccountDetailsForMainAccount(List<SubAccount> subAccounts){
        return subAccounts.stream()
                .map(mapper::mapToSubAccountDetails)
                .collect(Collectors.toList());
    }
}
