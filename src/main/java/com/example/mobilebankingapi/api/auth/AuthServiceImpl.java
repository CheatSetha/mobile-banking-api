package com.example.mobilebankingapi.api.auth;

import com.example.mobilebankingapi.api.auth.wep.RegisterDto;
import com.example.mobilebankingapi.api.user.User;
import com.example.mobilebankingapi.api.user.UserMapStruct;
import com.example.mobilebankingapi.utils.MailUtil;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;



import java.util.UUID;

@Service
@RequiredArgsConstructor

@Slf4j
public class AuthServiceImpl implements AuthService {

    private final AuthMapper authMapper;
    private final UserMapStruct userMapStruct;
    private final PasswordEncoder encoder;
    private final MailUtil mailUtil;

    @Value("${spring.mail.username}")
    private String appMail;

    @Override
    public void register(RegisterDto registerDto) {

        User user = userMapStruct.registerDtoToUser(registerDto);
        user.setIsVerified(false);
        user.setPassword(encoder.encode(user.getPassword()));

        log.info("User: {}", user.getEmail());
        authMapper.register(user);
    }

    @Override
    public void verify(String email) {

        User user = authMapper.selectByEmail(email).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Email not found"));

        user.setVerifiedCode(UUID.randomUUID().toString());

        MailUtil.Meta<?> meta = MailUtil.Meta.builder()
                .to(email)
                .from(appMail)
                .subject("Account Verification")
                .templateUrl("auth/verify")
                .data(user)
                .build();

        try {
            mailUtil.send(meta);
        } catch (MessagingException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getMessage());
        }

    }
}
