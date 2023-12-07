package com.example.library.rental.dto;

import com.example.library.rental.entity.Rental;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class RentalResponseDto {
    private Long rental_id;
    private String username;
    private String bookName;
    private Boolean bookRentalAble;
    private LocalDateTime rentalTime;
    private LocalDateTime returnTime;
    private Boolean bookReturn;

    public RentalResponseDto(Rental rental) {
        this.rental_id = rental.getId();
        this.username = rental.getUser().getUsername();
        this.bookName = rental.getBook().getBookName();
        this.rentalTime = rental.getRentalTime();
        this.returnTime = rental.getReturnTime();
        this.bookRentalAble = rental.getBook().getBookRentalAble();
        this.bookReturn = rental.getBookReturn();
    }
}
