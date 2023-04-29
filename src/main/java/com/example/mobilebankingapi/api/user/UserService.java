package com.example.mobilebankingapi.api.user;

import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;

import java.util.List;

public interface UserService {

    //can not use use pojo we have to use usedto
    UserDto createNewUser(CreateUserDto createUserDto);
    UserDto findUserById(Integer id);

    Integer deleteUserById(Integer id);
    Integer updateIsDeletedStatus(Integer id, boolean isDeleted);
    List<User> findAll();
//    get all user with pagination
    PageInfo<UserDto> findAllUser (int page , int limit);

    UserDto updateUser(Integer id, UpdateUserDto updateUserDto);
//    search user by name
    List<UserDto> searchUserByName(String name);
//    search user by studentCardId
    List<UserDto> searchUserByStudentCardId(String studentCardId);

    //search user by name or student card id
    List<UserDto> searchUserByNameOrStudentCardId(String name, String studentCardId);

}
