package com.example.mobilebankingapi.api.account;

import com.example.mobilebankingapi.api.user.CreateUserDto;
import com.example.mobilebankingapi.base.BaseRest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/{id}")
    public BaseRest<?> findAccountById(@PathVariable("id") Integer id){
        AccountDto account = accountService.findAccountById(id);
        return BaseRest.builder().status(true).code(HttpStatus.OK.value()).message("Account type have been found").timestamp(LocalDateTime.now())
                .data(account).build();
    }

    @DeleteMapping("/{id}")
    public BaseRest<?> deleteAccountById(@PathVariable("id") Integer id){
        Integer account = accountService.deleteAccountById(id);
        return BaseRest.builder().status(true).code(HttpStatus.OK.value()).message("Account type have been found").timestamp(LocalDateTime.now())
                .data(account).build();
    }

//create new account
    @PostMapping
    public BaseRest<?> createNewAcc (@RequestBody @Valid CreateAccountDto createAccountDto){
        AccountDto accountDto = accountService.createNewAccount(createAccountDto);
        log.info("DTO + {}",createAccountDto);
        return BaseRest.builder().status(true).
                code(HttpStatus.OK.value()).message("user have been created successfully")
                .timestamp(LocalDateTime.now()).data(accountDto).build();

    }
}
