package com.bookreview.controller;

import com.bookreview.service.BookService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;
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
    public BookDTO createBook(@RequestBody BookDTO bookDTO) {
        log.info("[BookController] createBook called with title: {}", bookDTO.getTitle());
        return bookService.createBook(bookDTO);
    }

    @PutMapping("/{id}")
    public BookDTO updateBook(@PathVariable Long id, @RequestBody BookDTO bookDTO) {
        log.info("[BookController] updateBook called for id: {}", id);
        return bookService.updateBook(id, bookDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        log.info("[BookController] deleteBook called for id: {}", id);
        bookService.deleteBook(id);
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
}
