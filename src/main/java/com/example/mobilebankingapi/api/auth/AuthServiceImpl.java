package com.example.mobilebankingapi.api.auth;

import com.example.mobilebankingapi.api.auth.wep.AuthDto;
import com.example.mobilebankingapi.api.auth.wep.LogInDto;
import com.example.mobilebankingapi.api.auth.wep.RegisterDto;
import com.example.mobilebankingapi.api.user.User;
import com.example.mobilebankingapi.api.user.UserMapStruct;
import com.example.mobilebankingapi.utils.MailUtil;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import java.util.stream.Collectors;
//@transactional for rollback if user role not created

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final AuthMapper authMapper;
    private final UserMapStruct userMapStruct;
    private final PasswordEncoder encoder;
    private final MailUtil mailUtil;
    private final JwtEncoder jwtEncoder;
    private final DaoAuthenticationProvider daoAuthenticationProvider;

    @Value("${spring.mail.username}")
    private String appMail;

    @Transactional
    @Override
    public void register(RegisterDto registerDto) {

        User user = userMapStruct.registerDtoToUser(registerDto);
        user.setIsVerified(false);
        user.setPassword(encoder.encode(user.getPassword()));

        log.info("User: {}", user.getEmail());
        if (authMapper.register(user)) {
//            create user role
            for (Integer role : registerDto.roleIds()) {
                log.info("id : {}", user.getId());
                authMapper.createUserRoles(user.getId(), role);
            }
        }
    }

    @Override
    public void verify(String email) {

        User user = authMapper.selectByEmail(email).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Email not found"));
        String verifiedCode = UUID.randomUUID().toString();

        if (authMapper.updateVerifiedCode(email, verifiedCode)) {
            user.setVerifiedCode(verifiedCode);

        } else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "user cannot be verified");
        }


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


    @Override
    public void checkVerify(String email, String verifiedCode) {

        User user = authMapper.selectByEmailAndVerified(email, verifiedCode).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Email or verified code not found"));
//if user exist set isVerified true and verifiedCode null after check verified code
        log.info("User: {}", user.getVerifiedCode());

        if (!user.getIsVerified()) {
            authMapper.verify(email, verifiedCode);
        }
    }

    @Override
    public AuthDto login(LogInDto logInDto) {
//        call spring security to authenticate user
        Authentication authentication = new UsernamePasswordAuthenticationToken(logInDto.email(), logInDto.password());
        authentication = daoAuthenticationProvider.authenticate(authentication);
        Instant now = Instant.now();
        // define scope
        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(auth -> !auth.startsWith("ROLE_"))
                .collect(Collectors.joining(" "));
//        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority("WRITE"));
//        authorities.add(new SimpleGrantedAuthority("READ"));
//        authorities.add(new SimpleGrantedAuthority("DELETE"));
//        authorities.add(new SimpleGrantedAuthority("UPDATE"));
//        authorities.add(new SimpleGrantedAuthority("FULL_CONTROL"));
//    String scope = authorities.stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.joining(" "));
        JwtClaimsSet claimsSet = JwtClaimsSet.builder().
                 issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.DAYS))
                .subject(authentication.getName())
                .claim("scope", scope)
                .build();
        String accessToken = jwtEncoder.encode(
                JwtEncoderParameters.from(claimsSet)
        ).getTokenValue();
        return new AuthDto("Bearer ", accessToken);
//        daoAuthenticationProvider.authenticate(authentication);
//        log.info("User: {}", authentication);
////        logic on basic authorization header
//        String basicAuthFormat = authentication.getName() + ":" + authentication.getCredentials();
//        String encoding = Base64.getEncoder().encodeToString(basicAuthFormat.getBytes());
////        Base64.getEncoder().encodeToString(basicAuthFormat.getBytes());
//        log.info("basic {}", encoding);
//
//
//
//        return new AuthDto(String.format("Basic %s", encoding));
    }
}
