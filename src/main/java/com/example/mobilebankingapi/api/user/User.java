package com.example.mobilebankingapi.api.user;

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
    private String email;
    private String password;
    private Boolean isVerified;
    private String verifiedCode;
    private List<Role> roles;
}
