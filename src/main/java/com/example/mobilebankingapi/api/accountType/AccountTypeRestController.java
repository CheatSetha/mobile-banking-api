package com.example.mobilebankingapi.api.accountType;

import com.example.mobilebankingapi.base.BaseRest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("api/v1/account-types")
@RequiredArgsConstructor
@Slf4j
public class AccountTypeRestController {
    private final AccountTypeService accountTypeService;
    @GetMapping
    //    <?> any type is okay
    public BaseRest<?> findAll() {
    var accountTypeDtoList = accountTypeService.findAll();
    return BaseRest.builder().status(true).code(HttpStatus.OK.value()).message("Account type have been found").timestamp(LocalDateTime.now())
            .data(accountTypeDtoList).build();


    }
    @PostMapping
    public BaseRest<?> createNewAccountType(@RequestBody @Valid CreateAccountTypeDto createAccountTypeDto){
        AccountTypeDto accountTypeDto = accountTypeService.createNewAccountType(createAccountTypeDto);
        log.info("DTO + {}", createAccountTypeDto);
        return BaseRest.builder().status(true).code(HttpStatus.OK.value()).message("Account type have been created").timestamp(LocalDateTime.now())
                .data(accountTypeDto).build();
    }
    @GetMapping("/{id}")
    public BaseRest<?> findAccountTypeById(@PathVariable("id") Integer id) {
        AccountTypeDto accountTypeDto = accountTypeService.findAccountTypeById(id);
        return BaseRest.builder().status(true).code(HttpStatus.OK.value()).message("Account type have been found").timestamp(LocalDateTime.now())
                .data(accountTypeDto).build();
    }
    @DeleteMapping("/{id}")
    public BaseRest<?> deleteAccountTypeById(@PathVariable("id") Integer id) {
        Integer deletedId = accountTypeService.deleteAccountTypeById(id);
        return BaseRest.builder().status(true).code(HttpStatus.OK.value()).message("Account type have been deleted").timestamp(LocalDateTime.now())
                .data(deletedId).build();
    }

    @PutMapping("/{id}")
    public BaseRest<?> updateAccountType(@PathVariable("id") Integer id, @RequestBody @Valid CreateAccountTypeDto createAccountTypeDto){
        AccountTypeDto accountTypeDto = accountTypeService.update(id, createAccountTypeDto);
        return BaseRest.builder().status(true).
                code(HttpStatus.OK.value()).
                message("Account type have been updated ").
                timestamp(LocalDateTime.now()).data(accountTypeDto).build();
    }
}
