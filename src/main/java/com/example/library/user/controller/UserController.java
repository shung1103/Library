package com.example.library.user.controller;

import com.example.library.common.dto.ApiResponseDto;
import com.example.library.common.security.UserDetailsImpl;
import com.example.library.user.dto.LoginRequestDto;
import com.example.library.user.dto.PasswordRequestDto;
import com.example.library.user.dto.SignupRequestDto;
import com.example.library.user.dto.UserResponseDto;
import com.example.library.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @Operation(summary = "회원가입")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "successful operation", content = @Content(schema = @Schema(implementation = ApiResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "bad request operation", content = @Content(schema = @Schema(implementation = ApiResponseDto.class)))
    })
    @PostMapping("/signup")
    public ResponseEntity<ApiResponseDto> signup(@Valid @RequestBody SignupRequestDto requestDto) {
        return userService.signup(requestDto);
    }

    @Operation(summary = "로그인 메서드")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = ApiResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "bad request operation", content = @Content(schema = @Schema(implementation = ApiResponseDto.class)))
    })
    @PostMapping("/login")
    public ResponseEntity<ApiResponseDto> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        return userService.login(loginRequestDto, response);
    }

    @Operation(summary = "로그아웃 메서드")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = ApiResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "bad request operation", content = @Content(schema = @Schema(implementation = ApiResponseDto.class)))
    })
    @PostMapping("/logout")
    public ResponseEntity<ApiResponseDto> logout(HttpServletResponse response, Authentication authResult) throws ServletException, IOException {
        return userService.logout(response, authResult);
    }

    @Operation(summary = "회원 정보 전체 조회")
    @Secured("ROLE_ADMIN")
    @GetMapping("/info-list")
    public ResponseEntity<List<UserResponseDto>> getUserList() {
        return userService.getUserList();
    }

    @Operation(summary = "회원 탈퇴")
    @DeleteMapping("/signout")
    public ResponseEntity<ApiResponseDto> signout(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody PasswordRequestDto passwordRequestDto,
                                                  HttpServletResponse response, Authentication authResult) throws ServletException, IOException {
        return userService.signout(userDetails.getUser(), passwordRequestDto, response, authResult);
    }
}
