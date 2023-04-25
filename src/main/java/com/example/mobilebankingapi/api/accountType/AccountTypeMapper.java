package com.example.mobilebankingapi.api.accountType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;


import java.util.List;

@Mapper

public interface AccountTypeMapper {
    //we need to define class and its method
    @SelectProvider(type = AccountTypeProvider.class, method = "buildSelectAllSql")
    List<AccountType> selectAll();

}
