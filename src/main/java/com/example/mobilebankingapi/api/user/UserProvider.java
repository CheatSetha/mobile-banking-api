package com.example.mobilebankingapi.api.user;

import org.apache.ibatis.jdbc.SQL;

public class UserProvider {
    private static final String  tableName = "users";
    public String buildInsertSql(){
        return new SQL(){{
            INSERT_INTO(tableName);
            VALUES("name", "#{user.name}");
            VALUES("gender","#{user.gender}");
            VALUES("one_signal_id","#{user.oneSignalId}");
            VALUES("student_card_id","#{user.studentCardId}");
            VALUES("is_student","#{user.isStudent}");
            VALUES("is_deleted","FALSE");

        }}.toString();
    }
}
