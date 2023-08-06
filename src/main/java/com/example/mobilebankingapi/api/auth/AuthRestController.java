package com.example.mobilebankingapi.api.auth;


import com.example.mobilebankingapi.api.auth.wep.AuthDto;
import com.example.mobilebankingapi.api.auth.wep.LogInDto;
import com.example.mobilebankingapi.api.auth.wep.RegisterDto;
import com.example.mobilebankingapi.api.auth.wep.TokenDto;
import com.example.mobilebankingapi.base.BaseRest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthRestController {

    private final AuthService authService;

    @PostMapping("/register")
    public BaseRest<?> register(@Valid @RequestBody RegisterDto registerDto) {

        // call service
        authService.register(registerDto);

        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("You have been registered successfully")
                .timestamp(LocalDateTime.now())
                .data(registerDto.email())
                .build();
    }

    @PostMapping("/verify")
    public BaseRest<?> verify(@RequestParam String email) {
        authService.verify(email);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Please check email and verify")
                .timestamp(LocalDateTime.now())
                .data(email)
                .build();
    }

    //    check verify code
    @GetMapping("/check-verify")
    public BaseRest<?> checkVerify(@RequestParam String email, @RequestParam String verifiedCode) {
        authService.checkVerify(email, verifiedCode);
        log.info("Email: {}", email);
        log.info("Verified Code: {}", verifiedCode);

        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("You have been verified successfully")
                .timestamp(LocalDateTime.now())
                .data(email)
                .build();
    }
//    FOR LOG IN
    @PostMapping("/login")
    public BaseRest<?> login(@Valid @RequestBody LogInDto logInDto) {
//        String token = authService.login(email, password);
        AuthDto authDto = authService.login(logInDto);
//        authService.login(logInDto);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("You have been logged in successfully")
                .timestamp(LocalDateTime.now())
                .data(authDto)
                .build();
    }
    @PostMapping("/refresh")
    public BaseRest<?> refresh(@RequestBody TokenDto tokenDto){
        AuthDto authDto = authService.refreshToken(tokenDto);
//        authService.login(logInDto);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Refresh token have been refreshed successfully")
                .timestamp(LocalDateTime.now())
                .data(authDto)
                .build();
    }

}
