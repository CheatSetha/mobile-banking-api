package com.example.mobilebankingapi.api.user;

import com.example.mobilebankingapi.api.auth.wep.RegisterDto;
import com.github.pagehelper.PageInfo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapStruct {
    //convert model to dto
    User createUserDtoToUser(CreateUserDto createUserDto);
    UserDto userToUserDto(User user);
    User userDtoToUser(UserDto userDto);
    User updateUserDtoToUser(UpdateUserDto updateUserDto);
    PageInfo<UserDto> userPageInfoToUserDtoPageInfo(PageInfo<User> userPageInfo);
    List<UserDto> userListToUserDtoList(List<User> userList);
    User registerDtoToUser(RegisterDto registerDto);
}
