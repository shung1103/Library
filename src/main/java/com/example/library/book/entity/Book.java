package com.example.library.book.entity;

import com.example.library.book.dto.BookRequestDto;
import com.example.library.common.entity.TimeStamped;
import com.example.library.rental.entity.Rental;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "books")
public class Book extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;

    @Column(name = "book_name", nullable = false)
    private String bookName;

    @Column(name = "book_author", nullable = false)
    private String bookAuthor;

    @Column(name = "book_intro", nullable = false)
    private String bookIntro;

    @Setter
    @Column(name = "book_rental_able", nullable = false)
    private Boolean bookRentalAble;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "book", cascade = CascadeType.PERSIST)
    private List<Rental> rentalList  = new ArrayList<>();

    public Book(BookRequestDto bookRequestDto) {
        this.bookName = bookRequestDto.getBookName();
        this.bookAuthor = bookRequestDto.getBookAuthor();
        this.bookIntro = bookRequestDto.getBookIntro();
        this.bookRentalAble = bookRequestDto.getBookRentalAble();
    }

    public void update(BookRequestDto bookRequestDto) {
        this.bookName = bookRequestDto.getBookName();
        this.bookAuthor = bookRequestDto.getBookAuthor();
        this.bookIntro = bookRequestDto.getBookIntro();
        this.bookRentalAble = bookRequestDto.getBookRentalAble();
    }
}
