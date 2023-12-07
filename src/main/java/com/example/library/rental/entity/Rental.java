package com.example.library.rental.entity;

import com.example.library.book.entity.Book;
import com.example.library.common.entity.TimeStamped;
import com.example.library.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "rentals")
public class Rental extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rental_id")
    private Long id;

    @Column(name = "rental_time", nullable = false)
    private LocalDateTime rentalTime;

    @Setter
    @Column(name = "return_time", nullable = false)
    private LocalDateTime returnTime;

    @Setter
    @Column(name = "book_return", nullable = false)
    private Boolean bookReturn;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id")
    private Book book;

    public Rental(Book book, User user) {
        this.book = book;
        this.user = user;
        this.rentalTime = LocalDateTime.now();
        this.returnTime = LocalDateTime.now().plusDays(7);
        this.bookReturn = false;
    }
}
