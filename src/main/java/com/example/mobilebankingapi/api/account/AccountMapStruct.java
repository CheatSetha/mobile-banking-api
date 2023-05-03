package com.example.mobilebankingapi.api.account;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapStruct {
//    convert pojo to dto
    List<AccountDto> toDto(List<Account> accounts);

}
