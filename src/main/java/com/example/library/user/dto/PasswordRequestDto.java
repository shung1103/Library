package com.example.library.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PasswordRequestDto {
    @NotBlank
    private String password;
}
