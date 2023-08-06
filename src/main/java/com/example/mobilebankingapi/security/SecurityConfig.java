package com.example.mobilebankingapi.security;

import com.example.mobilebankingapi.utils.KeyUtil;
import com.nimbusds.jose.KeySourceException;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSelector;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.util.List;
import java.util.UUID;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final PasswordEncoder encoder;
    private final UserDetailsService userDetailsService;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final KeyUtil keyUtil;


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
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setPasswordEncoder(encoder);
        auth.setUserDetailsService(userDetailsService);
        return auth;
    }

    @Bean(name = "jwtAuthenticationProvider")
    public JwtAuthenticationProvider jwtAuthenticationProvider(){
        JwtAuthenticationProvider provider = new JwtAuthenticationProvider(jwtRefreshTokenDecoder());
        provider.setJwtAuthenticationConverter(jwtAuthenticationConverter());
        return provider;

    }


    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // Disable CSRF in order to not use form login
        http.csrf(AbstractHttpConfigurer::disable);

        // Authorize URL mapping
        http.authorizeHttpRequests(auth -> {
            auth.requestMatchers("/api/v1/**").permitAll();

            auth.anyRequest().permitAll();
//            auth.requestMatchers(HttpMethod.GET,"/api/v1/account-types/**").hasAnyAuthority("SCOPE_account:read");
        });

        // Security mechanism for basic authentication
//        http.httpBasic();
        http.oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt
                        .jwtAuthenticationConverter(jwtAuthenticationConverter())
                )
        );

//        exception handling
        http.exceptionHandling(exception -> {
            exception.accessDeniedHandler(customAccessDeniedHandler);
            exception.authenticationEntryPoint(customAuthenticationEntryPoint);
        });

        // Make security http STATELESS
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();

    }

    // use to generate key pair public and private key
    @Bean
    public KeyPair keyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair();
    }

    //    agorithsm to generate key pair
    @Bean
    public RSAKey rsaKey(KeyPair keyPair) {
        return new RSAKey.Builder((RSAPublicKey) keyPair.getPublic())
                .privateKey(keyPair.getPrivate())
                .keyID(UUID.randomUUID().toString())
                .build();
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource(RSAKey rsaKey) {
        var jwkSet = new JWKSet(rsaKey);
        return new JWKSource<SecurityContext>() {
            @Override
            public List<JWK> get(JWKSelector jwkSelector, SecurityContext context) throws KeySourceException {
                return jwkSelector.select(jwkSet);
            }
        };
    }

    @Bean
    @Primary
    public JwtDecoder jwtAccessTokenDecoder() {
        return NimbusJwtDecoder.withPublicKey(keyUtil.getAccessTokenPublicKey()).build();

    }

    @Bean(name = "jwtRefreshTokenDecoder")
    public JwtDecoder jwtRefreshTokenDecoder() {
        return NimbusJwtDecoder.withPublicKey(keyUtil.getRefreshTokenPublicKey()).build();

    }

    //    in order to creat jwtEncoder we need jwkSource

    @Bean
    @Primary
    public JwtEncoder jwtAccessTokenEncoder() {

        JWK jwk = new RSAKey.Builder(keyUtil.getAccessTokenPublicKey())
                .privateKey(keyUtil.getAccessTokenPrivateKey())
                .build();
        return new NimbusJwtEncoder(((jwkSelector, securityContext) -> jwkSelector.select(new JWKSet(jwk))));

    }
    @Bean(name = "jwtRefreshTokenEncoder")
    public JwtEncoder jwtRefreshTokenEncoder() {
        JWK jwk = new RSAKey.Builder(keyUtil.getRefreshTokenPublicKey())
                .privateKey(keyUtil.getRefreshTokenPrivateKey())
                .build();
        return new NimbusJwtEncoder(((jwkSelector, securityContext) -> jwkSelector.select(new JWKSet(jwk))));

    }

}




