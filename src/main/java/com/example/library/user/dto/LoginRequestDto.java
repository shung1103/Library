package com.example.library.user.dto;

import com.example.library.common.entity.UserRoleEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto {
    private String username;
    private String password;
    @JsonIgnore
    private UserRoleEnum role;
}
