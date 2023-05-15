package com.example.mobilebankingapi.api.user;

import com.example.mobilebankingapi.api.auth.AuthService;
import com.example.mobilebankingapi.api.auth.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class User {
    private Integer id;
    private String name;
    private String gender;
    private String oneSignalId;
    private String studentCardId;
    private Boolean isStudent;
    private Boolean isDeleted;
    //    for auth purposes
    private String email;
    private String password;
    private Boolean isVerified;
    private String verifiedCode;

//    roles of user
    private List<Role> roles;
}
