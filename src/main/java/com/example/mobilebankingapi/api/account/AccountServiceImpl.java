package com.example.mobilebankingapi.api.account;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    @Override
    public AccountDto findAccountById(Integer id) {
        Account account = accountMapper.selectById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Account with id %d not found", id)));
        return accountMapStruct.AccounttoAccountDto(account);

    }

    @Override
    public Integer deleteAccountById(Integer id) {
        boolean isAccountExist = accountMapper.isAccountExist(id);
        if (isAccountExist){
            accountMapper.deleteById(id);
            return id;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Account with id %d not found", id));
    }

    @Override
    public AccountDto createNewAccount(CreateAccountDto createAccountDto) {
        Account account = accountMapStruct.createAccDtoToAcc(createAccountDto);
        accountMapper.insert(account);
        return accountMapStruct.AccounttoAccountDto(account);
    }
}
