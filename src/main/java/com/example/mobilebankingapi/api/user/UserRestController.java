package com.example.mobilebankingapi.api.user;

import com.example.mobilebankingapi.base.BaseRest;
import com.github.pagehelper.PageInfo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Slf4j
public class UserRestController {
    private final UserService userService;

    @PostMapping
    public BaseRest<?> createNewUser(@RequestBody @Valid CreateUserDto createUserDto) {
        UserDto userDto = userService.createNewUser(createUserDto);
        log.info("DTO + {}", createUserDto);
        return BaseRest.builder().status(true).code(HttpStatus.OK.value()).message("User have been created").timestamp(LocalDateTime.now())
                .data(userDto).build();
    }

    @GetMapping("/{id}")
    public BaseRest<?> findUserById(@PathVariable Integer id) {
        UserDto userDto = userService.findUserById(id);
        return BaseRest.builder().status(true).code(HttpStatus.OK.value()).message("User have been found").timestamp(LocalDateTime.now())
                .data(userDto).build();
    }

    @DeleteMapping("/{id}")
    public BaseRest<?> deleteUserById(@PathVariable Integer id) {
        Integer deletedId = userService.deleteUserById(id);

        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("User have been deleted")
                .timestamp(LocalDateTime.now())
                .data(deletedId).build();
    }

    @PutMapping("/{id}")
    public BaseRest<?> updateIsDeletedStatus(@PathVariable Integer id, @RequestBody IsDeletedDto dto) {
        Integer updatedId = userService.updateIsDeletedStatus(id, dto.status());
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("User have been deleted")
                .timestamp(LocalDateTime.now())
                .data(updatedId).build();
    }

    @GetMapping("/all")
    public BaseRest<?> findAll() {
        List<User> users = userService.findAll();
        return BaseRest.builder().status(true).code(HttpStatus.OK.value()).message("User have been found").timestamp(LocalDateTime.now())
                .data(users).build();
    }
//learn from cher
    @GetMapping
    public BaseRest<?> findAllUser(@RequestParam(name = "page", required = false, defaultValue = "1") int page,
                                   @RequestParam(name = "limit", required = false, defaultValue = "10") int limit) {
        PageInfo<UserDto> userDtoPageInfo = userService.findAllUser(page, limit);
        return BaseRest.builder().status(true).code(HttpStatus.OK.value()).message("User have been found").timestamp(LocalDateTime.now())
                .data(userDtoPageInfo).build();

    }


}
