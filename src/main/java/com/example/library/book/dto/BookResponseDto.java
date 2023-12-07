package com.example.library.book.dto;

import com.example.library.book.entity.Book;
import com.example.library.rental.dto.RentalResponseDto;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class BookResponseDto {
    private Long id;
    private String bookName;
    private String bookAuthor;
    private String bookIntro;
    private Boolean bookRentalAble;
    private List<RentalResponseDto> rentalList;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdAt;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime modifiedAt;

    public BookResponseDto(Book book) {
        this.id = book.getId();
        this.bookName = book.getBookName();
        this.bookAuthor = book.getBookAuthor();
        this.bookIntro = book.getBookIntro();
        this.bookRentalAble = book.getBookRentalAble();
        this.rentalList = book.getRentalList().stream().map(RentalResponseDto::new).collect(Collectors.toList());
        this.createdAt = book.getCreatedAt();
        this.modifiedAt = book.getModifiedAt();
    }
}
