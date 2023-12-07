package com.example.library.book.controller;

import com.example.library.book.dto.BookRequestDto;
import com.example.library.book.dto.BookResponseDto;
import com.example.library.book.service.BookService;
import com.example.library.common.dto.ApiResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BookController {
    private final BookService bookService;

    @Operation(summary = "도서 생성", description = "관리자 제한")
    @Transactional
    @Secured("ROLE_ADMIN")
    @PostMapping("/book")
    public ResponseEntity<BookResponseDto> createBook(@RequestBody BookRequestDto bookRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.createBook(bookRequestDto));
    }

    @Operation(summary = "도서 목록 조회", description = "로그인 불필요")
    @GetMapping("/books")
    public ResponseEntity<List<BookResponseDto>> getBookList() {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.getBookList());
    }

    @Operation(summary = "도서 단건 조회")
    @GetMapping("/book/{bookNo}")
    public ResponseEntity<BookResponseDto> getBookInfo(@PathVariable Long bookNo) {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.getBookInfo(bookNo));
    }

    @Operation(summary = "도서 정보 수정", description = "관리자 제한")
    @Transactional
    @Secured("ROLE_ADMIN")
    @PutMapping("/book/{bookNo}")
    public ResponseEntity<BookResponseDto> updateBookInfo(@PathVariable Long bookNo, @RequestBody BookRequestDto bookRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.updateBookInfo(bookNo, bookRequestDto));
    }

    @Operation(summary = "도서 정보 삭제", description = "관리자 제한")
    @Transactional
    @Secured("ROLE_ADMIN")
    @DeleteMapping("/book/{bookNo}")
    public ResponseEntity<ApiResponseDto> deleteBookInfo(@PathVariable Long bookNo) {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.deleteBookInfo(bookNo));
    }
}
