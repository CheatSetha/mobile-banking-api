package com.example.mobilebankingapi.api.user;

import lombok.RequiredArgsConstructor;

import java.util.List;

public interface UserService {

    //can not use use pojo we have to use usedto
    UserDto createNewUser(CreateUserDto createUserDto);
    UserDto findUserById(Integer id);

}
