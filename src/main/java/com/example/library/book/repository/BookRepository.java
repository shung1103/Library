package com.example.library.book.repository;

import com.example.library.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByBookName(String bookName);

    List<Book> findAllByOrderByBookNameDesc();
}
