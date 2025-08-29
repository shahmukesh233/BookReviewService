package com.bookreview.controller;

import com.bookreview.dto.FavoriteBookDTO;
import com.bookreview.service.FavoriteBookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import java.util.Collections;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FavoriteBookControllerTest {
    @Mock
    private FavoriteBookService favoriteBookService;
    @InjectMocks
    private FavoriteBookController favoriteBookController;

    @Test
    void getFavoritesByUser_shouldReturnResponseEntity() {
        when(favoriteBookService.getFavoritesByUser(anyLong())).thenReturn(Collections.emptyList());
        ResponseEntity<List<FavoriteBookDTO>> response = favoriteBookController.getFavoritesByUser(1L);
        assertTrue(response.getBody().isEmpty());
    }
}
