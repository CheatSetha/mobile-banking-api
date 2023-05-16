package com.example.mobilebankingapi.api.auth;

import org.apache.ibatis.jdbc.SQL;

public class AuthProvider {
    public static final String TABLE_NAME = "users";

    public String buildRegisterSql() {
        return new SQL() {{
            INSERT_INTO("users");
            VALUES("email", "#{u.email}");
            VALUES("password", "#{u.password}");
            VALUES("is_verified", "#{u.isVerified}");
            VALUES("is_deleted", "FALSE");

        }}.toString();

    }

    public String buildSelectByEmailAndVerifiedCodeSql() {
        return new SQL() {{
            SELECT("*");
            FROM(TABLE_NAME);
            WHERE("email = #{email}");
            WHERE("verified_code = #{verifiedCode}");
            WHERE("is_deleted = FALSE");
        }}.toString();
    }

    public String buildVerifySql() {
        return new SQL() {{
            UPDATE(TABLE_NAME);
            SET("is_verified = TRUE");
            SET("verified_code = NULL");
            WHERE("email = #{email}");
            WHERE("verified_code = #{verifiedCode}");
        }}.toString();
    }

    public String buildUpdateVerifiedCodeSql() {
        return new SQL() {{
            UPDATE(TABLE_NAME);
            SET("verified_code = #{verifiedCode}");
            WHERE("email = #{email}");
        }}.toString();
    }

    public String buildCreateUserRolesSql() {
        return new SQL() {{
            INSERT_INTO("users_roles");
            VALUES("user_id", "#{userId}");
            VALUES("role_id", "#{roleId}");
        }}.toString();
    }
    public String buildLoadUserRolesSql() {
        return new SQL() {{
            SELECT("r.id, r.name");
            FROM("roles AS r");
            INNER_JOIN("users_roles AS ur ON ur.role_id  = r.id");
            WHERE("ur.user_id = #{userId}");
        }}.toString();
    }
}
