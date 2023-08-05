package com.example.mobilebankingapi.api.auth;


import com.example.mobilebankingapi.api.user.Authority;
import com.example.mobilebankingapi.api.user.Role;
import com.example.mobilebankingapi.api.user.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Mapper
@Repository
public interface AuthMapper {

    @InsertProvider(type = AuthProvider.class, method = "buildRegisterSql")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    boolean register(@Param("u") User user);



    @Select("SELECT * FROM users WHERE email = #{email} AND is_deleted = FALSE")
    @Results(id = "authResultMap", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "studentCardId", column = "student_card_id"),
            @Result(property = "isStudent", column = "is_student"),
            @Result(property = "isVerified", column = "is_verified"),
            @Result(property = "verifiedCode", column = "verified_code"),
            @Result(property = "roles", column = "id",
                    many = @Many(select = "loadUserRoles"))
    })
    Optional<User> selectByEmail(@Param("email") String email);


    @Select("SELECT * FROM users WHERE email = #{email} AND is_deleted = FALSE AND is_verified = TRUE")
    @ResultMap("authResultMap")
    Optional<User> loadUserByUsername(@Param("email") String email);



    @InsertProvider(type = AuthProvider.class, method = "buildCreateUserRolesSql")
    boolean createUserRoles(@Param("userId") Integer userId, @Param("roleId") Integer roleId);


    @SelectProvider(type = AuthProvider.class, method = "buildSelectByEmailAndVerifiedCodeSql")
    @ResultMap("authResultMap")
    Optional<User> selectByEmailAndVerified(@Param("email") String email, @Param("verifiedCode") String verifiedCode);


    @UpdateProvider(type = AuthProvider.class, method = "buildVerifySql")
    void verify(@Param("email") String email, @Param("verifiedCode") String verifiedCode);


    @UpdateProvider(type = AuthProvider.class, method = "buildUpdateVerifiedCodeSql")
    boolean updateVerifiedCode(@Param("email") String email, @Param("verifiedCode") String verifiedCode);


    @SelectProvider(type = AuthProvider.class, method = "buildLoadUserRolesSql")
    @Result(column = "id", property = "authorities",
            many = @Many(select = "loadUserAuthorities"))
    List<Role> loadUserRoles(@Param("userId") Integer userId);


    @SelectProvider(type = AuthProvider.class, method = "buildLoadUserAuthoritiesSql")
    List<Authority > loadUserAuthorities(Integer roleId);

}

