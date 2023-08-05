package com.example.mobilebankingapi.api.user;

import org.apache.ibatis.jdbc.SQL;

public class UserProvider {
    private static final String  tableName = "users";
    public String buildInsertSql(){
        return new SQL(){{
            INSERT_INTO(tableName);
            VALUES("name", "#{u.name}");
            VALUES("gender","#{u.gender}");
            VALUES("one_signal_id","#{u.oneSignalId}");
            VALUES("student_card_id","#{u.studentCardId}");
            VALUES("is_student","#{u.isStudent}");
        }}.toString();

    }
    public String buildSelectByIdSql(){
        return new SQL(){{
            SELECT("*");
            FROM(tableName);
            WHERE("id = #{id}");
        }}.toString();
    }

    public String buildDeleteByIdSql(){
        return new SQL(){{
            DELETE_FROM(tableName);
            WHERE("id = #{id}");
        }}.toString();
    }
    public String buildUpdateIsDeletedStatusSql(){
        return new SQL(){{
            UPDATE(tableName);
            SET("is_deleted = #{isDeleted}");
            WHERE("id = #{id}");
        }}.toString();
    }
    public String buildSelectAllSql(){
        return new SQL(){{
            SELECT("*");
            FROM(tableName);
        }}.toString();
    }

//    with pagination

    public String buildSelectSql(){
        return new SQL(){{
            SELECT("*");
            FROM(tableName);
            WHERE("is_deleted = false");
            ORDER_BY("id DESC");
        }}.toString();
    }
    public String buildUpdateSql(){
        return new SQL(){{
            UPDATE(tableName);
            SET("name = #{u.name}");
            SET("gender = #{u.gender}");
            WHERE("id = #{u.id}");
        }}.toString();

    }

    //search user by name or student card id
    public String buildSearchSql(){
        return new SQL(){{
            SELECT("*");
            FROM(tableName);
            WHERE("name ILIKE CONCAT('%',#{name},'%')");
            OR();
            WHERE("student_card_id ILIKE CONCAT('%',#{studentCardId},'%')");
        }}.toString();
    }

}
