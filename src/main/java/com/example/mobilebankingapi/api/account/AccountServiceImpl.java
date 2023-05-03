package com.example.mobilebankingapi.api.account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService{
    private final AccountMapper accountMapper;
    private final AccountMapStruct accountMapStruct;
    @Override
    public List<AccountDto> findAll() {
        List<Account> accounts = accountMapper.selectAll();
        System.out.println(accounts);
        return accountMapStruct.toDto(accounts);
    }
}
