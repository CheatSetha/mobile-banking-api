package com.example.mobilebankingapi.api.accountType;

import java.util.List;

public interface AccountTypeService {
    //we have to follow dao and dto pattern
    List<AccountTypeDto> findAll();
    AccountTypeDto createNewAccountType(CreateAccountTypeDto createAccountTypeDto);
    AccountTypeDto findAccountTypeById(Integer id);
    Integer deleteAccountTypeById(Integer id);
    AccountTypeDto update(Integer id, CreateAccountTypeDto createAccountTypeDto);

}
