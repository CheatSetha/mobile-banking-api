package com.example.mobilebankingapi.api.user;

import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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


//    get all user with pagination
    @Override
    public PageInfo<UserDto> findAllUser(int page, int limit) {
//        PageHelper.startPage(page, limit);

       PageInfo<User> userPageInfo =  PageHelper.startPage(page, limit).doSelectPageInfo(userMapper::select);
//       convert userPageInfo to userDtoPageInfo
//        bcuz we can't return userPageInfo to client
//        we have to convert it to userDtoPageInfo. it's a dto
       return userMapStruct.userPageInfoToUserDtoPageInfo(userPageInfo);
    }

    @Override
    public UserDto updateUser(Integer id, UpdateUserDto updateUserDto) {
      if (userMapper.isUserExist(id)){
          User user = userMapStruct.updateUserDtoToUser(updateUserDto);
          user.setId(id);
          userMapper.update(user);
          return this.findUserById(id);
      }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("User with id %d not found", id));
    }

    @Override
    public List<UserDto> searchUserByName(String name) {
        List<User> users = userMapper.searchByName(name);
        if (users.size() > 0){
            return userMapStruct.userListToUserDtoList(users);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("User with name %s not found", name));
    }

    @Override
    public List<UserDto> searchUserByStudentCardId(String studentCardId) {
       List<User> users = userMapper.searchByStudentIdCard(studentCardId);
       if (users.size()>0){
           return userMapStruct.userListToUserDtoList(users);
       }
       throw  new ResponseStatusException(HttpStatus.NOT_FOUND,
               String.format("user with studentCardId %s not found",studentCardId));
    }



    @Override
    public List<UserDto> searchUserByNameOrStudentCardId(String name, String studentCardId) {
        List<User> users = userMapper.search(name, studentCardId);
        if(users.size() > 0){
            return userMapStruct.userListToUserDtoList(users);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("User with name %s or studentCardId %s not found", name, studentCardId));
    }
}
