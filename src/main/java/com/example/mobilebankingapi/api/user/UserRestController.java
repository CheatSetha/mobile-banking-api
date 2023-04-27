package com.example.mobilebankingapi.api.user;

import com.example.mobilebankingapi.base.BaseRest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

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
}
