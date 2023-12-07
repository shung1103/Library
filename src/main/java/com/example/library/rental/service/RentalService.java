package com.example.library.rental.service;

import com.example.library.book.entity.Book;
import com.example.library.book.repository.BookRepository;
import com.example.library.common.dto.ApiResponseDto;
import com.example.library.rental.dto.RentalResponseDto;
import com.example.library.rental.entity.Rental;
import com.example.library.rental.repository.RentalRepository;
import com.example.library.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RentalService {
    private final RentalRepository rentalRepository;
    private final BookRepository bookRepository;

    public ResponseEntity<ApiResponseDto> createRental(Long bookNo, User user) {
        List<Rental> rentalList = rentalRepository.findAllByUserId(user.getId());
        for (Rental rental : rentalList) {
            if (!rental.getBookReturn() && rental.getReturnTime().isBefore(LocalDateTime.now())) {
                user.setUserRentalAble(false);
            }
        }

        Book book = bookRepository.findById(bookNo).orElseThrow(() -> new NullPointerException("해당 번호의 도서가 존재하지 않습니다."));
        if (book.getBookRentalAble() && user.getUserRentalAble()) {
            Rental rental = new Rental(book, user);
            rental.getBook().setBookRentalAble(false);
            rentalRepository.save(rental);
            return ResponseEntity.status(201).body(new ApiResponseDto("도서 대출 성공", HttpStatus.CREATED.value()));
        } else {
            throw new IllegalArgumentException("도서 대출을 진행할 수 없습니다.");
        }
    }

    public ResponseEntity<List<RentalResponseDto>> getRentalList() {
        List<Rental> rentalList = rentalRepository.findAll();
        List<RentalResponseDto> rentalResponseDtoList = new ArrayList<>();

        for (Rental rental : rentalList) {
            rentalResponseDtoList.add(new RentalResponseDto(rental));
        }

        return ResponseEntity.status(HttpStatus.OK).body(rentalResponseDtoList);
    }

    public ResponseEntity<List<RentalResponseDto>> getRentalInfo(User user) {
        List<Rental> rentalList = rentalRepository.findAllByUserId(user.getId());
        List<RentalResponseDto> rentalResponseDtoList = new ArrayList<>();

        for (Rental rental : rentalList) {
            rentalResponseDtoList.add(new RentalResponseDto(rental));
        }

        return ResponseEntity.status(HttpStatus.OK).body(rentalResponseDtoList);
    }

    public ResponseEntity<ApiResponseDto> returnRentalBook(Long rentalNo, User user) {
        Rental rental = rentalRepository.findById(rentalNo).orElseThrow(() -> new NullPointerException("존재하지 않는 대출번호입니다."));
        if (user.getId().equals(rental.getUser().getId())) {
            rental.getBook().setBookRentalAble(true);
            rental.setReturnTime(LocalDateTime.now());
            rental.setBookReturn(true);
            user.setUserRentalAble(true);

            List<Rental> rentalList = rentalRepository.findAllByUserId(user.getId());
            for (Rental rental1 : rentalList) {
                if (rental1.getReturnTime().isBefore(LocalDateTime.now())) {
                    user.setUserRentalAble(false);
                }
            }

            return ResponseEntity.status(200).body(new ApiResponseDto("도서 반납 성공", HttpStatus.OK.value()));
        } else {
            throw new IllegalArgumentException("해당 대출항목에 대한 권한이 없습니다.");
        }
    }
}
