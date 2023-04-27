package com.example.mobilebankingapi.api.user;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Mapper
@Repository
public interface UserMapper {
    @InsertProvider(type = UserProvider.class, method = "buildInsertSql")
    @Options(useGeneratedKeys = true, keyProperty = "u.id")
    void insert(@Param("u") User user);
    @SelectProvider(type = UserProvider.class, method = "buildSelectByIdSql")
    Optional<User> selectById(@Param("id") Integer   id);
}
