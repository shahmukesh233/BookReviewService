package com.bookreview.controller;

import com.bookreview.dto.FavoriteBookDTO;
import com.bookreview.service.FavoriteBookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FavoriteBookCrudControllerTest {
    @Mock
    private FavoriteBookService favoriteBookService;
    @InjectMocks
    private FavoriteBookCrudController favoriteBookCrudController;

    @Test
    void getFavorite_shouldReturnResponseEntity() {
        FavoriteBookDTO dto = new FavoriteBookDTO();
        when(favoriteBookService.getFavorite(anyLong())).thenReturn(dto);
        ResponseEntity<FavoriteBookDTO> response = favoriteBookCrudController.getFavorite(1L);
        assertEquals(dto, response.getBody());
    }
}
