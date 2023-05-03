package com.example.mobilebankingapi.api.account;

import com.example.mobilebankingapi.api.accountType.AccountType;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AccountMapper {
    //    @SelectProvider(type = AccountProvider.class, method = "buildSelectAllSql")
//    List<Account> selectAll();
    @Results(
            id = "accountResultMap", value ={
            @Result(column = "account_no", property = "accountNo"),
            @Result(column = "account_name", property = "accountName"),
            @Result(column = "profile", property = "profile"),
            @Result(column = "pin", property = "pin"),
            @Result(column = "phone_number", property = "phoneNumber"),
            @Result(column = "transfer_limit", property = "transferLimit"),
            @Result(column = "account_type", property = "accountType", javaType = AccountType.class, one = @One(select = "selectAccountTypes")),
    })
    @Select("SELECT * FROM accounts")
    List<Account> selectAll();
    @Select("SELECT * FROM account_types WHERE id = #{id}")
    AccountType  selectAccountTypes(@Param("id") Integer id);
}
