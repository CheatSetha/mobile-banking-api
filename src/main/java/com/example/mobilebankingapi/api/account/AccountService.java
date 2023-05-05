package com.example.mobilebankingapi.api.account;

import java.util.List;

public interface AccountService {
    List<AccountDto> findAll();
//    find by id
    AccountDto findAccountById(Integer id);

//    delete account by checking is exist
    Integer deleteAccountById(Integer id);
//    create account
    AccountDto createNewAccount(CreateAccountDto createAccountDto);
}
