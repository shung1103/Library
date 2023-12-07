package com.example.library.user.dto;

import com.example.library.common.entity.UserRoleEnum;
import com.example.library.rental.dto.RentalResponseDto;
import com.example.library.user.entity.User;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class UserResponseDto {
    private String username;
    private UserRoleEnum role;
    private String email;
    private List<RentalResponseDto> rentalList;

    public UserResponseDto(User user) {
        this.username = user.getUsername();
        this.role = user.getRole();
        this.email = user.getEmail();
        this.rentalList = user.getRentalList().stream().map(RentalResponseDto::new).collect(Collectors.toList());
    }
}
