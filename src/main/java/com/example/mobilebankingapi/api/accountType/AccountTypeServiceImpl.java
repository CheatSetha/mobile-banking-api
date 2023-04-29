package com.example.mobilebankingapi.api.accountType;

import com.example.mobilebankingapi.api.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountTypeServiceImpl implements AccountTypeService {
    private final AccountTypeMapper accountTypeMapper;
    private final AccountTypeMapStruct accountTypeMapStruct;
    @Override
    public List<AccountTypeDto> findAll() {
        List<AccountType> accountTypes = accountTypeMapper.selectAll();

        return accountTypeMapStruct.toDto(accountTypes);
    }

    @Override
    public AccountTypeDto createNewAccountType(CreateAccountTypeDto createAccountTypeDto) {
        AccountType accountType = accountTypeMapStruct.createAccountTypeDtoToAccountType(createAccountTypeDto);
        accountTypeMapper.insert(accountType);
        return accountTypeMapStruct.accountTypeToAccountTypeDto(accountType);
    }

    @Override
    public AccountTypeDto findAccountTypeById(Integer id) {
        AccountType accountType = accountTypeMapper.selectById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("AccountType with id %d not found", id)));
        return accountTypeMapStruct.accountTypeToAccountTypeDto(accountType);
    }

    @Override
    public Integer deleteAccountTypeById(Integer id) {
        boolean isAccountTypeExist = accountTypeMapper.isAccountTypeExist(id);
        System.out.println(isAccountTypeExist);
        if (isAccountTypeExist) {
            accountTypeMapper.deleteById(id);
            return id;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("AccountType with id %d not found", id));
    }

    @Override
    public AccountTypeDto update(Integer id, CreateAccountTypeDto createAccountTypeDto) {
        if (accountTypeMapper.isAccountTypeExist(id)){
            AccountType accountType = accountTypeMapStruct.createAccountTypeDtoToAccountType(createAccountTypeDto);
            accountType.setId(id);
            accountTypeMapper.update(accountType);
            return this.findAccountTypeById(id);
        }
    throw  new ResponseStatusException(HttpStatus.NOT_FOUND,
            String.format("Account Type with id %d not found",id));
    }
}
