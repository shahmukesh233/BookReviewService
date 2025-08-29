
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {
    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    @Mock
    private Authentication authentication;

    @Test
    void createBook_shouldReturnBookDTOForAdmin() {
        BookDTO dto = new BookDTO();
        GrantedAuthority adminAuthority = mock(GrantedAuthority.class);
        when(adminAuthority.getAuthority()).thenReturn("ROLE_ADMIN");
        List<GrantedAuthority> authorities = Collections.singletonList(adminAuthority);
    doReturn(authorities).when(authentication).getAuthorities();
        when(bookService.createBook(any(BookDTO.class))).thenReturn(dto);
        ResponseEntity<?> response = bookController.createBook(dto, authentication);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(dto, response.getBody());
    }

    @Test
    void createBook_shouldReturnForbiddenForNonAdmin() {
        BookDTO dto = new BookDTO();
        GrantedAuthority userAuthority = mock(GrantedAuthority.class);
        when(userAuthority.getAuthority()).thenReturn("ROLE_USER");
        List<GrantedAuthority> authorities = Collections.singletonList(userAuthority);
    doReturn(authorities).when(authentication).getAuthorities();
        ResponseEntity<?> response = bookController.createBook(dto, authentication);
        assertEquals(403, response.getStatusCode().value());
        assertEquals("Access denied: Only ADMIN can create books.", response.getBody());
    }

    @Test
    void updateBook_shouldReturnBookDTOForAdmin() {
        BookDTO dto = new BookDTO();
        GrantedAuthority adminAuthority = mock(GrantedAuthority.class);
        when(adminAuthority.getAuthority()).thenReturn("ROLE_ADMIN");
        List<GrantedAuthority> authorities = Collections.singletonList(adminAuthority);
    doReturn(authorities).when(authentication).getAuthorities();
        when(bookService.updateBook(anyLong(), any(BookDTO.class))).thenReturn(dto);
        ResponseEntity<?> response = bookController.updateBook(1L, dto, authentication);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(dto, response.getBody());
    }

    @Test
    void updateBook_shouldReturnForbiddenForNonAdmin() {
        BookDTO dto = new BookDTO();
        GrantedAuthority userAuthority = mock(GrantedAuthority.class);
        when(userAuthority.getAuthority()).thenReturn("ROLE_USER");
        List<GrantedAuthority> authorities = Collections.singletonList(userAuthority);
    doReturn(authorities).when(authentication).getAuthorities();
        ResponseEntity<?> response = bookController.updateBook(1L, dto, authentication);
        assertEquals(403, response.getStatusCode().value());
        assertEquals("Access denied: Only ADMIN can update books.", response.getBody());
    }

    @Test
    void deleteBook_shouldReturnSuccessForAdmin() {
        GrantedAuthority adminAuthority = mock(GrantedAuthority.class);
        when(adminAuthority.getAuthority()).thenReturn("ROLE_ADMIN");
        List<GrantedAuthority> authorities = Collections.singletonList(adminAuthority);
    doReturn(authorities).when(authentication).getAuthorities();
        ResponseEntity<?> response = bookController.deleteBook(1L, authentication);
        assertEquals(200, response.getStatusCode().value());
        assertEquals("Book deleted successfully.", response.getBody());
    }

    @Test
    void deleteBook_shouldReturnForbiddenForNonAdmin() {
        GrantedAuthority userAuthority = mock(GrantedAuthority.class);
        when(userAuthority.getAuthority()).thenReturn("ROLE_USER");
        List<GrantedAuthority> authorities = Collections.singletonList(userAuthority);
    doReturn(authorities).when(authentication).getAuthorities();
        ResponseEntity<?> response = bookController.deleteBook(1L, authentication);
        assertEquals(403, response.getStatusCode().value());
        assertEquals("Access denied: Only ADMIN can delete books.", response.getBody());
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
