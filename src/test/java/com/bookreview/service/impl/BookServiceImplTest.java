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
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {
    @Mock
    private BookRepository bookRepository;
    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    void getBooksPage_shouldReturnPageOfBookDTO() {
        Book book = Book.builder().id(1L).title("Title").author("Author").build();
        Page<Book> bookPage = new PageImpl<>(Collections.singletonList(book));
        when(bookRepository.findAll(any(PageRequest.class))).thenReturn(bookPage);
        Page<BookDTO> result = bookService.getBooksPage(PageRequest.of(0, 1));
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void createBook_shouldSaveBook() {
        BookDTO dto = new BookDTO();
        dto.setTitle("Title");
        dto.setAuthor("Author");
        Book book = Book.builder().title("Title").author("Author").build();
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        BookDTO result = bookService.createBook(dto);
        assertEquals("Title", result.getTitle());
    }
}
