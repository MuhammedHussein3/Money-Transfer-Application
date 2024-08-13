package com.bank.subAccount.mapper;

import com.bank.subAccount.dto.SubAccountDetailsResponse;
import com.bank.subAccount.dto.SubAccountRequestDto;
import com.bank.subAccount.dto.SubAccountResponse;
import com.bank.subAccount.entity.SubAccount;
import org.springframework.stereotype.Service;

@Service
public class SubAccountMapper {
    public SubAccount mapToSubAccount(SubAccountRequestDto requestDto) {

        return SubAccount.builder()
                .accountNumberId(requestDto.accountNumberId())
                .subAccountType(requestDto.subAccountType())
                .balance(requestDto.balance())
                .subAccountName(requestDto.subAccountName())
                .build();
    }

    public SubAccountResponse mapToSubAccountResponse(SubAccount subAccount) {
        return SubAccountResponse.builder()
                .subAccountId(subAccount.getId())
                .build();
    }

    public SubAccountDetailsResponse mapToSubAccountDetails(SubAccount subAccount) {
        return SubAccountDetailsResponse
                .builder()
                .subAccountName(subAccount.getSubAccountName())
                .subAccountType(subAccount.getSubAccountType())
                .balance(subAccount.getBalance())
                .build();
    }
}
