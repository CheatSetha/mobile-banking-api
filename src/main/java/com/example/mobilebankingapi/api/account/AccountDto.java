package com.example.mobilebankingapi.api.account;

import com.example.mobilebankingapi.api.accountType.AccountType;
import lombok.Builder;

public record AccountDto(
        String accountNo,
        String accountName,
        String profile,
        Integer pin,
        String phoneNumber,
        Integer transferLimit,
        AccountType accountType
) {
}
