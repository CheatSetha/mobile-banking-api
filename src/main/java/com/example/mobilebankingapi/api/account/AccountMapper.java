package com.example.mobilebankingapi.api.account;

import com.example.mobilebankingapi.api.accountType.AccountType;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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


    @Select("SELECT EXISTS(SELECT * FROM accounts WHERE id = #{id})")
    boolean isAccountExist(@Param("id") Integer id);

    @ResultMap("accountResultMap")
    @SelectProvider(type = AccountProvider.class, method = "buildSelectByIdSql")
    Optional<Account> selectById(@Param("id") Integer id);


    @Delete("DELETE FROM accounts WHERE id = #{id}")
    void deleteById(@Param("id") Integer id);

//    create new account
    @InsertProvider(type = AccountProvider.class, method = "buildInsertSql")
    @Options(useGeneratedKeys = true, keyProperty = "a.id")
    void insert(@Param("a")Account account);
}
