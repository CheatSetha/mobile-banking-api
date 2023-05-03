package com.example.mobilebankingapi.api.account;

import com.example.mobilebankingapi.api.accountType.AccountType;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
public class Account {
    private Integer id;
    private String accountNo;
    private String accountName;
    private String profile;
    private Integer pin;
    private String phoneNumber;
    private Integer transferLimit;
    private AccountType accountType;
}
