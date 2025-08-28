package com.bookreview.service.impl;

import com.bookreview.dto.BookDTO;
import com.bookreview.model.Book;
import com.bookreview.repository.BookRepository;
import com.bookreview.service.BookService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class BookServiceImpl implements BookService {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(BookServiceImpl.class);
    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public org.springframework.data.domain.Page<BookDTO> getBooksPage(org.springframework.data.domain.Pageable pageable) {
        log.info("[BookServiceImpl] getBooksPage called for page: {}, size: {}", pageable.getPageNumber(), pageable.getPageSize());
        return bookRepository.findAll(pageable).map(book -> {
            BookDTO dto = new BookDTO();
            dto.setId(book.getId());
            dto.setTitle(book.getTitle());
            dto.setAuthor(book.getAuthor());
            dto.setDescription(book.getDescription());
            dto.setCoverImage(book.getCoverImage());
            dto.setGenres(book.getGenres());
            dto.setPublishedYear(book.getPublishedYear());
            dto.setAvgRating(book.getAvgRating());
            dto.setReviewCount(book.getReviewCount());
            return dto;
        });
    }

    @Override
    public void updateBookRating(Long bookId, Double avgRating, Integer reviewCount) {
        log.info("[BookServiceImpl] updateBookRating called for bookId: {}, avgRating: {}, reviewCount: {}", bookId, avgRating, reviewCount);
        Book book = bookRepository.findById(bookId).orElseThrow();
        book.setAvgRating(avgRating);
        book.setReviewCount(reviewCount);
        bookRepository.save(book);
    }
    
    @Override
    public BookDTO createBook(BookDTO bookDTO) {
    log.info("[BookServiceImpl] createBook called with title: {}", bookDTO.getTitle());
    Book book = Book.builder()
        .title(bookDTO.getTitle())
        .author(bookDTO.getAuthor())
        .description(bookDTO.getDescription())
        .coverImage(bookDTO.getCoverImage())
        .genres(bookDTO.getGenres())
        .publishedYear(bookDTO.getPublishedYear())
        .build();
    Book saved = bookRepository.save(book);
    bookDTO.setId(saved.getId());
    return bookDTO;
    }

    @Override
    public BookDTO updateBook(Long id, BookDTO bookDTO) {
    log.info("[BookServiceImpl] updateBook called for id: {}", id);
    Book book = bookRepository.findById(id).orElseThrow();
    book.setTitle(bookDTO.getTitle());
    book.setAuthor(bookDTO.getAuthor());
    book.setDescription(bookDTO.getDescription());
    book.setCoverImage(bookDTO.getCoverImage());
    book.setGenres(bookDTO.getGenres());
    book.setPublishedYear(bookDTO.getPublishedYear());
    Book updated = bookRepository.save(book);
    bookDTO.setId(updated.getId());
    return bookDTO;
    }

    @Override
    public void deleteBook(Long id) {
    log.info("[BookServiceImpl] deleteBook called for id: {}", id);
        bookRepository.deleteById(id);
    }

    @Override
    public BookDTO getBookById(Long id) {
    log.info("[BookServiceImpl] getBookById called for id: {}", id);
    Book book = bookRepository.findById(id).orElseThrow();
    BookDTO dto = new BookDTO();
    dto.setId(book.getId());
    dto.setTitle(book.getTitle());
    dto.setAuthor(book.getAuthor());
    dto.setDescription(book.getDescription());
    dto.setCoverImage(book.getCoverImage());
    dto.setGenres(book.getGenres());
    dto.setPublishedYear(book.getPublishedYear());
    dto.setAvgRating(book.getAvgRating());
    dto.setReviewCount(book.getReviewCount());
    return dto;
    }

    @Override
    public List<BookDTO> getAllBooks() {
    log.info("[BookServiceImpl] getAllBooks called");
        return bookRepository.findAll().stream().map(book -> {
            BookDTO dto = new BookDTO();
            dto.setId(book.getId());
            dto.setTitle(book.getTitle());
            dto.setAuthor(book.getAuthor());
            dto.setDescription(book.getDescription());
            dto.setCoverImage(book.getCoverImage());
            dto.setGenres(book.getGenres());
            dto.setPublishedYear(book.getPublishedYear());
            dto.setAvgRating(book.getAvgRating());
            dto.setReviewCount(book.getReviewCount());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public Page<BookDTO> searchBooks(String keyword, Pageable pageable) {
        log.info("[BookServiceImpl] searchBooks called with keyword: {}, page: {}, size: {}", keyword, pageable.getPageNumber(), pageable.getPageSize());
        return bookRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(keyword, keyword, pageable)
                .map(book -> {
                    BookDTO dto = new BookDTO();
                    dto.setId(book.getId());
                    dto.setTitle(book.getTitle());
                    dto.setAuthor(book.getAuthor());
                    dto.setDescription(book.getDescription());
                    dto.setCoverImage(book.getCoverImage());
                    dto.setGenres(book.getGenres());
                    dto.setPublishedYear(book.getPublishedYear());
                    dto.setAvgRating(book.getAvgRating());
                    dto.setReviewCount(book.getReviewCount());
                    return dto;
                });
    }
}
