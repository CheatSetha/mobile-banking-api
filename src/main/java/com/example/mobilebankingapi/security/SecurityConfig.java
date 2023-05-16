package com.example.mobilebankingapi.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final PasswordEncoder encoder;
    private final UserDetailsService userDetailsService;

    // Define in-memory user
    /*
    The Authentication Provider is backed by a simple,
     in-memory implementation, InMemoryUserDetailsManager.
     This is useful for rapid prototyping when a full persistence mechanism is not yet necessary
     */
//    @Bean
//    public InMemoryUserDetailsManager userDetailsService() {
//        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password(encoder.encode("123"))
//                .roles("ADMIN")
//                .build();
//        UserDetails goldUser = User.builder()
//                .username("gold")
//                .password(encoder.encode("123"))
//                .roles("ADMIN", "ACCOUNT")
//                .build();
//        UserDetails user = User.builder()
//                .username("user")
//                .password(encoder.encode("123"))
//                .roles("USER")
//                .build();
//        userDetailsManager.createUser(admin);
//        userDetailsManager.createUser(goldUser);
//        userDetailsManager.createUser(user);
//        return userDetailsManager;
//    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setPasswordEncoder(encoder);
        auth.setUserDetailsService(userDetailsService);
        return auth;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // Disable CSRF
        http.csrf(AbstractHttpConfigurer::disable);

        // Authorize URL mapping
        http.authorizeHttpRequests(request -> {
            request.requestMatchers("/api/v1/auth/**").permitAll();
            request.requestMatchers(HttpMethod.GET,"/api/v1/users/**", "/api/v1/files/**").hasAnyRole("SYSTEM", "ADMIN");
            request.requestMatchers(HttpMethod.POST,"/api/v1/users/**").hasRole("SYSTEM");
            request.anyRequest().authenticated();
        });

        // Security mechanism
        http.httpBasic();

        // Make security http STATELESS
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }

}

