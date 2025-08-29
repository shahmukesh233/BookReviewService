package com.bookreview.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FavoriteBookDTOTest {
    @Test
    void testGettersSetters() {
        FavoriteBookDTO dto = new FavoriteBookDTO();
        dto.setId(1L);
        dto.setUserId(2L);
        dto.setBookId(3L);
        dto.setBookTitle("Title");
        dto.setBookAuthor("Author");
        dto.setBookGenres("Genres");
        assertEquals(1L, dto.getId());
        assertEquals(2L, dto.getUserId());
        assertEquals(3L, dto.getBookId());
        assertEquals("Title", dto.getBookTitle());
        assertEquals("Author", dto.getBookAuthor());
        assertEquals("Genres", dto.getBookGenres());
    }
}
