package com.bookreview.controller;

import com.bookreview.service.BookService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import com.bookreview.dto.BookDTO;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/api/books")
@Slf4j
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<?> createBook(@RequestBody BookDTO bookDTO, Authentication authentication) {
        if (!hasAdminRole(authentication)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied: Only ADMIN can create books.");
        }
        log.info("[BookController] createBook called with title: {}", bookDTO.getTitle());
        return ResponseEntity.ok(bookService.createBook(bookDTO));
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateBook(@PathVariable Long id, @RequestBody BookDTO bookDTO, Authentication authentication) {
        if (!hasAdminRole(authentication)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied: Only ADMIN can update books.");
        }
        log.info("[BookController] updateBook called for id: {}", id);
        return ResponseEntity.ok(bookService.updateBook(id, bookDTO));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id, Authentication authentication) {
        if (!hasAdminRole(authentication)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied: Only ADMIN can delete books.");
        }
        log.info("[BookController] deleteBook called for id: {}", id);
        bookService.deleteBook(id);
        return ResponseEntity.ok("Book deleted successfully.");
    }
    

    @GetMapping("/{id}")
    public BookDTO getBookById(@PathVariable Long id) {
        log.info("[BookController] getBookById called for id: {}", id);
        return bookService.getBookById(id);
    }

    @GetMapping
    public List<BookDTO> getAllBooks() {
        log.info("[BookController] getAllBooks called");
        return bookService.getAllBooks();
    }

    @GetMapping("/page")
    public Page<BookDTO> getBooksPage(@RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        int zeroBasedPage = Math.max(page - 1, 0);
        log.info("[BookController] getBooksPage called for page: {} (zero-based: {}), size: {}", page, zeroBasedPage, size);
        Pageable pageable = PageRequest.of(zeroBasedPage, size);
        return bookService.getBooksPage(pageable);
    }

    @GetMapping("/search")
    public Page<BookDTO> searchBooks(@RequestParam String keyword,
                                    @RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "10") int size) {
        int zeroBasedPage = Math.max(page - 1, 0);
        log.info("[BookController] searchBooks called with keyword: {}, page: {} (zero-based: {}), size: {}", keyword, page, zeroBasedPage, size);
        Pageable pageable = PageRequest.of(zeroBasedPage, size);
        return bookService.searchBooks(keyword, pageable);
    }

    @PutMapping("/{bookId}/rating")
    public void updateBookRating(@PathVariable Long bookId, @RequestBody BookDTO bookDTO) {
        log.info("[BookController] updateBookRating called for bookId: {}, avgRating: {}, reviewCount: {}", bookId, bookDTO.getAvgRating(), bookDTO.getReviewCount());
        bookService.updateBookRating(bookId, bookDTO.getAvgRating(), bookDTO.getReviewCount());
    }

    private boolean hasAdminRole(Authentication authentication) {
        if (authentication == null || authentication.getAuthorities() == null) return false;
        return authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    @GetMapping("/recommendations/top-rated")
    public List<BookDTO> getTopRatedBooks(@RequestParam(defaultValue = "5") int limit) {
        log.info("[BookController] getTopRatedBooks called, limit: {}", limit);
        return bookService.getTopRatedBooks(limit);
    }

    @GetMapping("/recommendations/similar")
    public List<BookDTO> getSimilarBooks(@RequestParam Long userId) {
        log.info("[BookController] getSimilarBooks called for userId: {}", userId);
        return bookService.getSimilarBooks(userId);
    }
}
