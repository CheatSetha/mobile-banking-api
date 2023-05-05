package com.example.mobilebankingapi.api.account;

import org.apache.ibatis.jdbc.SQL;

public class AccountProvider {
    private static final String tableName = "accounts";
    public String buildSelectByIdSql(){
        return new SQL(){{
            SELECT("*");
            FROM("accounts");
            WHERE("id = #{id}");
        }}.toString();
    }

    public String buildInsertSql(){
        return  new SQL(){{
            INSERT_INTO(tableName);
            VALUES("account_no","#{a.accountNo}");
            VALUES("account_name","#{a.accountName}");
            VALUES("profile","#{a.profile}");
            VALUES("pin","#{a.pin}");
            VALUES("password","#{a.password}");
            VALUES("phone_number","#{a.phoneNumber}");
            VALUES("transfer_limit","#{a.transferLimit}");
            VALUES("account_type","#{a.accountType}");


        }}.toString();
    }

}
