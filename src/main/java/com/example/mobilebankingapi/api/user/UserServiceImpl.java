package com.example.mobilebankingapi.api.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
        return this.findUserById(user.getId());
    }

    @Override
    public UserDto findUserById(Integer id) {
        User user = userMapper.selectById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("User with id %d not found", id)));
        return userMapStruct.userToUserDto(user);
    }

    @Override
    public Integer deleteUserById(Integer id) {
        boolean isUserExist = userMapper.isUserExist(id);
        System.out.println(isUserExist);
        if (isUserExist) {
            userMapper.deleteById(id);
            return id;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("User with id %d not found", id));
    }

    @Override
    public Integer updateIsDeletedStatus(Integer id, boolean isDeleted) {
        boolean isUserExist = userMapper.isUserExist(id);
        if (isUserExist){
            userMapper.updateIsDeletedStatus(id,isDeleted);
            return id;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("User with id %d not found", id));
    }

    @Override
    public List<User> findAll() {
        return userMapper.selectAll();
    }



}
