package com.example.library.book.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class BookRequestDto {
    @NotBlank(message = "필수 입력 값입니다.")
    private String bookName;

    @NotBlank(message = "필수 입력 값입니다.")
    private String bookAuthor;

    @NotBlank(message = "필수 입력 값입니다.")
    private String bookIntro;

    @JsonIgnore
    private Boolean bookRentalAble = true;
}
