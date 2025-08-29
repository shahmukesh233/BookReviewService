package com.bookreview.repository;

import com.bookreview.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    Page<Book> findAll(Pageable pageable);
    Page<Book> findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(String title, String author, Pageable pageable);

    // Top-rated books
    List<Book> findTop10ByAvgRatingIsNotNullOrderByAvgRatingDesc();

    // Books by genre
    List<Book> findByGenresContainingIgnoreCase(String genre);
}
