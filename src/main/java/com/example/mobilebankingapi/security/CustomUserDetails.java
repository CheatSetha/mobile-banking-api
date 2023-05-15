package com.example.mobilebankingapi.security;

import com.example.mobilebankingapi.api.user.User;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomUserDetails implements UserDetails {
//    userDetail is an interface that contains the following methods:
//    it's use for authentication and authorization
// getAuthorities() - returns a collection of authorities granted to the user
//    getAuthorities() set up roles and permissions

    private User user;
//    in order to get roles and permissions we need to override getAuthorities() method
//    and return a collection of authorities granted to the user
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles();
    }

//    getPassword() - returns the password used to authenticate the user
    @Override
    public String getPassword() {
        return user.getPassword() ;
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
//    isCredentialsNonExpired() - indicates whether the user's credentials (password) has expired.
//    Expired credentials prevent authentication.

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
//    isEnabled() - indicates whether the user is enabled or disabled. A disabled user cannot be authenticated.
    @Override
    public boolean isEnabled() {
        return true;
    }
}
