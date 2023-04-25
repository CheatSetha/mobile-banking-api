package com.example.mobilebankingapi.api.accountType;

import org.apache.ibatis.jdbc.SQL;

public class AccountTypeProvider {
    public String buildSelectAllSql(){
        return new SQL(){{
            //todo;
            SELECT("*");
            FROM("account_types");

        }}.toString();
    }
}
