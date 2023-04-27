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
    @Result(column = "student_card_id", property = "studentCardId")
    @Result(column = "is_student", property = "isStudent")
    Optional<User> selectById(@Param("id") Integer   id);
    @Select("SELECT EXISTS(SELECT * FROM users WHERE id = #{id})")
    boolean isUserExist(@Param("id") Integer id);
    @DeleteProvider(type = UserProvider.class, method = "buildDeleteByIdSql")
    void deleteById(@Param("id") Integer id);
    @UpdateProvider(type = UserProvider.class, method = "buildUpdateIsDeletedStatusSql")
    void updateIsDeletedStatus(@Param("id") Integer id , @Param("isDeleted") Boolean isDeleted);

}
