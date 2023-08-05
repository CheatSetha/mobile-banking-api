package com.example.mobilebankingapi.security;

import com.example.mobilebankingapi.api.auth.AuthMapper;
import com.example.mobilebankingapi.api.user.Authority;
import com.example.mobilebankingapi.api.user.Role;
import com.example.mobilebankingapi.api.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

private  final AuthMapper authMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = authMapper.loadUserByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));
        log.info("User: {}", user);
        for (Role role : user.getRoles()){
            for (Authority authority : role.getAuthorities()){
                System.out.println(authority.getName());
            }
        }
        CustomUserDetails customUserDetails = new CustomUserDetails();
        customUserDetails.setUser(user);
      log.info("CustomUserDetails: {}", customUserDetails.getAuthorities());
        return customUserDetails;
    }
}
