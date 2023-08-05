package com.example.mobilebankingapi.api.accountType;

import org.apache.ibatis.jdbc.SQL;

public class AccountTypeProvider {
    private static final String tableName = "account_types";

    public String buildSelectAllSql() {
        return new SQL() {{
            //todo;
            SELECT("*");
            FROM(tableName);

        }}.toString();
    }

    public String buildInsertSql() {
        return new SQL() {{
            INSERT_INTO(tableName);
            VALUES("name", "#{u.name}");
        }}.toString();
    }

    public String buildSelectByIdSql() {
        return new SQL() {{
            SELECT("*");
            FROM(tableName);
            WHERE("id = #{id}");
        }}.toString();
    }

    public String buildDeleteByIdSql() {
        return new SQL() {{
            DELETE_FROM(tableName);
            WHERE("id = #{id}");
        }}.toString();
    }
    public String buildUpdateSql() {
        return new SQL() {{
            UPDATE(tableName);
            SET("name = #{a.name}");
            WHERE("id = #{a.id}");
        }}.toString();
    }
}
