package com.bookreview.service.impl;

import com.bookreview.dto.BookDTO;
import com.bookreview.model.Book;
import com.bookreview.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import java.util.Collections;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class BookServiceImplFullTest {
    @Mock
    private BookRepository bookRepository;
    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    void updateBook_shouldUpdateBook() {
        Book book = Book.builder().id(1L).build();
        BookDTO dto = new BookDTO();
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        BookDTO result = bookService.updateBook(1L, dto);
        assertEquals(1L, result.getId());
    }

    @Test
    void deleteBook_shouldCallRepository() {
        bookService.deleteBook(1L);
        verify(bookRepository, times(1)).deleteById(1L);
    }

    @Test
    void getBookById_shouldReturnDTO() {
        Book book = Book.builder().id(1L).build();
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        BookDTO result = bookService.getBookById(1L);
        assertEquals(1L, result.getId());
    }

    @Test
    void getAllBooks_shouldReturnList() {
        Book book = Book.builder().id(1L).build();
        when(bookRepository.findAll()).thenReturn(Collections.singletonList(book));
        List<BookDTO> result = bookService.getAllBooks();
        assertEquals(1, result.size());
    }

    @Test
    void searchBooks_shouldReturnPage() {
        Book book = Book.builder().id(1L).build();
        Page<Book> page = new PageImpl<>(Collections.singletonList(book));
        when(bookRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(anyString(), anyString(), any(PageRequest.class))).thenReturn(page);
        Page<BookDTO> result = bookService.searchBooks("test", PageRequest.of(0, 1));
        assertEquals(1, result.getTotalElements());
    }
}
