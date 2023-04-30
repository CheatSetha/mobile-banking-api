package com.example.mobilebankingapi.api.accountType;

import java.util.List;

public interface AccountTypeService {
    //we have to follow dao and dto pattern
//    service layer is responsible for business logic
//    dao layer is responsible for database logic
//    dto layer is responsible for data transfer
//    service use both dto and pojo
//    dao use only pojo
//    whe we define a service we have to define a dto and a pojo
//    dto used when we declare servie

    List<AccountTypeDto> findAll();
    AccountTypeDto createNewAccountType(CreateAccountTypeDto createAccountTypeDto);
    AccountTypeDto findAccountTypeById(Integer id);
    Integer deleteAccountTypeById(Integer id);
    AccountTypeDto update(Integer id, CreateAccountTypeDto createAccountTypeDto);

}
