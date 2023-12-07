package com.example.library.user.service;

import com.example.library.common.dto.ApiResponseDto;
import com.example.library.common.entity.UserRoleEnum;
import com.example.library.common.security.JwtUtil;
import com.example.library.user.dto.LoginRequestDto;
import com.example.library.user.dto.PasswordRequestDto;
import com.example.library.user.dto.SignupRequestDto;
import com.example.library.user.dto.UserResponseDto;
import com.example.library.user.entity.User;
import com.example.library.user.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Value("${ADMIN_TOKEN}")
    private String ADMIN_TOKEN;

    public ResponseEntity<ApiResponseDto> signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());
        String email = requestDto.getEmail();

        // 회원 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 ID가 존재합니다.");
        }

        Optional<User> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 Email 입니다.");
        }

        // 사용자 권한 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (requestDto.isAdmin()) {
            if (!ADMIN_TOKEN.equals(requestDto.getAdminToken())) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        User user = new User(username, password, email, role, requestDto.getUserRentalAble());
        userRepository.save(user);

        return ResponseEntity.status(201).body(new ApiResponseDto("회원가입 성공", HttpStatus.CREATED.value()));
    }

    public ResponseEntity<ApiResponseDto> login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        log.info("login 시도");
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다."));

        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(loginRequestDto.getUsername(), loginRequestDto.getRole()));
        jwtUtil.addJwtToCookie(response.getHeader(JwtUtil.AUTHORIZATION_HEADER), response);

        return ResponseEntity.ok().body(new ApiResponseDto("로그인에 성공했습니다.", HttpStatus.OK.value()));
    }

    public ResponseEntity<ApiResponseDto> logout(HttpServletResponse response, Authentication authResult) throws ServletException, IOException {
        jwtUtil.deleteCookie(response, authResult);
        return ResponseEntity.status(200).body(new ApiResponseDto("로그아웃 성공", HttpStatus.OK.value()));
    }

    public ResponseEntity<List<UserResponseDto>> getUserList() {
        List<User> userList = userRepository.findAll();
        List<UserResponseDto> userResponseDtoList = new ArrayList<>();

        for (User user : userList) {
            userResponseDtoList.add(new UserResponseDto(user));
        }

        return ResponseEntity.status(HttpStatus.OK).body(userResponseDtoList);
    }

    public ResponseEntity<ApiResponseDto> signout(User user, PasswordRequestDto passwordRequestDto, HttpServletResponse response, Authentication authResult) throws ServletException, IOException {
        if (passwordEncoder.matches(passwordRequestDto.getPassword(), user.getPassword())) {
            userRepository.delete(user);
            jwtUtil.deleteCookie(response, authResult);
            return ResponseEntity.status(200).body(new ApiResponseDto("회원탈퇴 성공", HttpStatus.OK.value()));
        } else {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }
}
