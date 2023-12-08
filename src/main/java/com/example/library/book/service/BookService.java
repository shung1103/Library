package com.example.library.book.service;

import com.example.library.book.dto.BookRequestDto;
import com.example.library.book.dto.BookResponseDto;
import com.example.library.book.entity.Book;
import com.example.library.book.repository.BookRepository;
import com.example.library.common.aws.AwsS3Service;
import com.example.library.common.dto.ApiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final AwsS3Service awsS3Service;

    @CacheEvict(value = "Books", allEntries = true, cacheManager = "bookCacheManager")
    public BookResponseDto createBook(BookRequestDto bookRequestDto) {
        // 책 이름 중복 확인
        if (bookRepository.findByBookName(bookRequestDto.getBookName()).isPresent()) {
            throw new IllegalArgumentException("중복된 도서이름이 존재합니다.");
        } else {
            Book book = new Book(bookRequestDto);
            bookRepository.save(book);
            return new BookResponseDto(book);
        }
    }

    @Cacheable(value = "Books", cacheManager = "bookCacheManager")
    public List<BookResponseDto> getBookList() {
        List<Book> bookList = bookRepository.findAllByOrderByBookNameDesc();
        List<BookResponseDto> bookResponseDtoList = new ArrayList<>();

        for (Book book : bookList) {
            bookResponseDtoList.add(new BookResponseDto(book));
        }

        return bookResponseDtoList;
    }

    @Cacheable(value = "Books", key = "#bookNo", cacheManager = "bookCacheManager")
    public BookResponseDto getBookInfo(Long bookNo) {
        Book book = bookRepository.findById(bookNo).orElseThrow(() -> new NullPointerException("해당 번호의 도서가 존재하지 않습니다."));
        return new BookResponseDto(book);
    }

    @CacheEvict(value = "Books", allEntries = true, cacheManager = "bookCacheManager")
    public BookResponseDto updateBookInfo(Long bookNo, BookRequestDto bookRequestDto) {
        Book book = bookRepository.findById(bookNo).orElseThrow(() -> new NullPointerException("해당 번호의 도서가 존재하지 않습니다."));

        book.update(bookRequestDto);
        bookRepository.save(book);
        return new BookResponseDto(book);
    }

    @CacheEvict(value = "Books", allEntries = true, cacheManager = "bookCacheManager")
    public ApiResponseDto deleteBookInfo(Long bookNo) {
        Book book = bookRepository.findById(bookNo).orElseThrow(() -> new NullPointerException("해당 번호의 도서가 존재하지 않습니다."));
        bookRepository.delete(book);
        return new ApiResponseDto("도서가 삭제 되었습니다.", HttpStatus.OK.value());
    }
}
