package com.example.mobilebankingapi.api.account;

import com.example.mobilebankingapi.api.accountType.AccountType;

public record CreateAccountDto(
        String accountNo,
        String accountName,
        String profile,
        Integer pin,
        String password,
        String phoneNumber,
        Integer transferLimit,
        AccountType accountType
) {
}
