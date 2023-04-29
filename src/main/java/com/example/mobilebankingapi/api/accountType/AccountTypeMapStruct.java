package com.example.mobilebankingapi.api.accountType;

import org.mapstruct.Mapper;

import java.util.List;

//not my batis mapper
@Mapper(componentModel = "spring")
public interface AccountTypeMapStruct {
    //convert model to dto
     List<AccountTypeDto> toDto(List<AccountType> model);
    //convert dto to model
    AccountTypeDto accountTypeToAccountTypeDto(AccountType accountType);
    AccountType createAccountTypeDtoToAccountType(CreateAccountTypeDto createAccountTypeDto);

}
