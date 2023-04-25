package com.example.mobilebankingapi.api.accountType;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountTypeServiceImpl implements AccountTypeService {
    private final AccountTypeMapper accountTypeMapper;
    private final AccountTypeMapStruct accountTypeMapStruct;
    @Override
    public List<AccountTypeDto> findAll() {
        List<AccountType> accountTypes = accountTypeMapper.selectAll();
//        List<AccountTypeDto> accountTypeDtosList = accountTypes.stream().
//                map(accountType -> new AccountTypeDto(accountType.getName())).toList();
//        use mapstruct instead

        return accountTypeMapStruct.toDto(accountTypes);
    }
}
