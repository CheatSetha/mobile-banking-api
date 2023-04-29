package com.example.mobilebankingapi.api.accountType;
import org.apache.ibatis.annotations.*;


import java.util.List;
import java.util.Optional;

@Mapper

public interface AccountTypeMapper {
    //we need to define class and its method
    @SelectProvider(type = AccountTypeProvider.class, method = "buildSelectAllSql")
    List<AccountType> selectAll();
    @InsertProvider(type = AccountTypeProvider.class, method = "buildInsertSql")
    @Result(column = "name", property = "name")
    void insert(@Param("u") AccountType accountType);
    @SelectProvider(type = AccountTypeProvider.class, method = "buildSelectByIdSql")
    Optional<AccountType> selectById(@Param("id") Integer id);
    @Select("SELECT EXISTS(SELECT * FROM account_types WHERE id = #{id})")
    boolean isAccountTypeExist(@Param("id") Integer id);

    @DeleteProvider(type = AccountTypeProvider.class, method = "buildDeleteByIdSql")
    void deleteById(@Param("id") Integer id);
    @UpdateProvider(type = AccountTypeProvider.class, method = "buildUpdateSql")
    void update(@Param("a") AccountType accountType);


}

