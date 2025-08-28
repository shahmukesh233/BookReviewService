package com.bookreview.repository;

import com.bookreview.model.FavoriteBook;
import com.bookreview.model.User;
import com.bookreview.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface FavoriteBookRepository extends JpaRepository<FavoriteBook, Long> {
    List<FavoriteBook> findByUser(User user);
    Optional<FavoriteBook> findByUserAndBook(User user, Book book);
    void deleteByUserAndBook(User user, Book book);
}
