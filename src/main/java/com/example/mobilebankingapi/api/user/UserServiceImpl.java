package com.example.mobilebankingapi.api.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements  UserService {
    private final UserMapper userMapper;
    private final UserMapStruct userMapStruct;


    @Override
    public UserDto createNewUser(CreateUserDto createUserDto) {
//        userMapper.insert();
        User user = userMapStruct.createUserDtoToUser(createUserDto);
        userMapper.insert(user);
        return userMapStruct.userToUserDto(user);
    }
}
