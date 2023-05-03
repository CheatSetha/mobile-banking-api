package com.example.mobilebankingapi.api.account;

import com.example.mobilebankingapi.base.BaseRest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/v1/accounts")
@RequiredArgsConstructor
@Slf4j
public class AccountController {
    private final AccountService accountService;
    @GetMapping
    public BaseRest<?> findALl(){
        List<AccountDto> accounts = accountService.findAll();
        return BaseRest.builder().status(true).code(HttpStatus.OK.value()).message("Account type have been found").timestamp(LocalDateTime.now())
                .data(accounts).build();
    }
}
