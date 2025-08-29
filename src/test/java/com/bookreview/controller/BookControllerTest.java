package com.bookreview.controller;

import com.bookreview.dto.BookDTO;
import com.bookreview.service.BookService;
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
import java.util.List;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {
    @Mock
    private BookService bookService;
    @InjectMocks
    private BookController bookController;

    @Test
    void createBook_shouldReturnBookDTO() {
        BookDTO dto = new BookDTO();
        when(bookService.createBook(any(BookDTO.class))).thenReturn(dto);
        BookDTO result = bookController.createBook(dto);
        assertEquals(dto, result);
    }

    @Test
    void getAllBooks_shouldReturnList() {
        when(bookService.getAllBooks()).thenReturn(Collections.emptyList());
        List<BookDTO> result = bookController.getAllBooks();
        assertTrue(result.isEmpty());
    }

    @Test
    void getBooksPage_shouldReturnPage() {
        Page<BookDTO> page = new PageImpl<>(Collections.emptyList());
        when(bookService.getBooksPage(any(PageRequest.class))).thenReturn(page);
        Page<BookDTO> result = bookController.getBooksPage(1, 10);
        assertEquals(0, result.getTotalElements());
    }
}
