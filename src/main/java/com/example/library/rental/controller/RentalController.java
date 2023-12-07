package com.example.library.rental.controller;

import com.example.library.common.dto.ApiResponseDto;
import com.example.library.common.security.UserDetailsImpl;
import com.example.library.rental.dto.RentalResponseDto;
import com.example.library.rental.service.RentalService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rental")
public class RentalController {
    private final RentalService rentalService;

    @Operation(summary = "도서 대출 생성")
    @Transactional
    @PostMapping("/{bookNo}")
    public ResponseEntity<ApiResponseDto> createRental(@PathVariable Long bookNo, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return rentalService.createRental(bookNo, userDetails.getUser());
    }

    @Operation(summary = "도서 대출 전체 현황 조회")
    @Secured("ROLE_ADMIN")
    @GetMapping("/list")
    public ResponseEntity<List<RentalResponseDto>> getRentalList() {
        return rentalService.getRentalList();
    }

    @Operation(summary = "도서 대출 본인 현황 조회")
    @GetMapping("/info")
    public ResponseEntity<List<RentalResponseDto>> getRentalInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return rentalService.getRentalInfo(userDetails.getUser());
    }

    @Operation(summary = "도서 대출 반납 처리")
    @Transactional
    @PutMapping("/back/{rentalNo}")
    public ResponseEntity<ApiResponseDto> returnRentalBook(@PathVariable Long rentalNo, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return rentalService.returnRentalBook(rentalNo, userDetails.getUser());
    }
}
