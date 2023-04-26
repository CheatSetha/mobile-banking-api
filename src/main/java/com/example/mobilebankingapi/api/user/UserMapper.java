package com.example.mobilebankingapi.api.user;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {
    @InsertProvider(type = UserProvider.class, method = "buildInsertSql")
    void insert(@Param("user") User user);
}
