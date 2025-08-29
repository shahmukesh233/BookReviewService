package com.bookreview.service;

import com.bookreview.dto.BookDTO;
import java.util.List;
import org.springframework.data.domain.Page;

public interface BookService {
    BookDTO createBook(BookDTO bookDTO);
    BookDTO updateBook(Long id, BookDTO bookDTO);
    void deleteBook(Long id);
    BookDTO getBookById(Long id);
    List<BookDTO> getAllBooks();
    Page<BookDTO> searchBooks(String keyword, org.springframework.data.domain.Pageable pageable);
    Page<BookDTO> getBooksPage(org.springframework.data.domain.Pageable pageable);
    void updateBookRating(Long bookId, Double avgRating, Integer reviewCount);

    List<BookDTO> getTopRatedBooks(int limit);
    List<BookDTO> getSimilarBooks(Long userId);
}
