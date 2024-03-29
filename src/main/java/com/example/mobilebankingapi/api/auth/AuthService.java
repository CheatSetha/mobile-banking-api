package com.example.mobilebankingapi.api.auth;

import com.example.mobilebankingapi.api.auth.wep.AuthDto;
import com.example.mobilebankingapi.api.auth.wep.LogInDto;
import com.example.mobilebankingapi.api.auth.wep.RegisterDto;
import com.example.mobilebankingapi.api.auth.wep.TokenDto;

public interface AuthService {
    void register(RegisterDto registerDto);

    void verify(String email);
//    check verify code
    void checkVerify(String email, String verifiedCode);
    AuthDto login(LogInDto logInDto);
    AuthDto refreshToken (TokenDto tokenDto);



}
