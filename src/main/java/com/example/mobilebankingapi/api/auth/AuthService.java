package com.example.mobilebankingapi.api.auth;

import com.example.mobilebankingapi.api.auth.wep.RegisterDto;

public interface AuthService {
    void register(RegisterDto registerDto);

    void verify(String email);
}
