package com.example.mobilebankingapi.api.user;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Mapper
@Repository
public interface UserMapper {
    @InsertProvider(type = UserProvider.class, method = "buildInsertSql")
    @Options(useGeneratedKeys = true, keyProperty = "u.id")
    void insert(@Param("u") User user);
    @SelectProvider(type = UserProvider.class, method = "buildSelectByIdSql")
    @Results(id = "userResultMap", value = {
            @Result(column = "student_card_id", property = "studentCardId"),
            @Result(column = "is_student", property = "isStudent"),
            @Result(column = "is_deleted", property = "isDeleted")
    })
    Optional<User> selectById(@Param("id") Integer   id);
    @Select("SELECT EXISTS(SELECT * FROM users WHERE id = #{id})")
    boolean isUserExist(@Param("id") Integer id);
    @DeleteProvider(type = UserProvider.class, method = "buildDeleteByIdSql")
    void deleteById(@Param("id") Integer id);
    @UpdateProvider(type = UserProvider.class, method = "buildUpdateIsDeletedStatusSql")
    void updateIsDeletedStatus(@Param("id") Integer id , @Param("isDeleted") Boolean isDeleted);
    @SelectProvider(type = UserProvider.class, method = "buildSelectAllSql")
    @ResultMap("userResultMap")
    List<User> selectAll();

//    with pagination
    @SelectProvider(type = UserProvider.class, method = "buildSelectSql")
    @ResultMap("userResultMap")
    List<User> select();

    @ResultMap("userResultMap")
    @UpdateProvider(type = UserProvider.class, method = "buildUpdateSql")
    void update(@Param("u") User user);

//    search user by name
    @Select("SELECT * FROM users WHERE name ILIKE CONCAT('%',#{name},'%')")
    @ResultMap("userResultMap")
    List<User> searchByName(@Param("name") String name);

//    search user by student id card
    @Select("SELECT * FROM users WHERE student_card_id ILIKE CONCAT('%',#{studentCardId},'%')")
    @ResultMap("userResultMap")
    List<User> searchByStudentIdCard(@Param("studentCardId") String studentCardId);

//    search user by name or student card id
    @SelectProvider(type = UserProvider.class, method = "buildSearchSql")
    @ResultMap("userResultMap")
    List<User> search(@Param("name") String name, @Param("studentCardId") String studentCardId);


}
