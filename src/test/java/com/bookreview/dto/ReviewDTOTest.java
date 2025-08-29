package com.bookreview.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;

class ReviewDTOTest {
    @Test
    void testGettersSetters() {
        ReviewDTO dto = new ReviewDTO();
        dto.setId(1L);
        dto.setRating(5);
        dto.setComment("Great");
        dto.setBookId(2L);
        dto.setUserId(3L);
        dto.setCreatedAt(LocalDateTime.now());
        dto.setModifiedAt(LocalDateTime.now());
        dto.setBookTitle("BookTitle");
        assertEquals(1L, dto.getId());
        assertEquals(5, dto.getRating());
        assertEquals("Great", dto.getComment());
        assertEquals(2L, dto.getBookId());
        assertEquals(3L, dto.getUserId());
        assertNotNull(dto.getCreatedAt());
        assertNotNull(dto.getModifiedAt());
        assertEquals("BookTitle", dto.getBookTitle());
    }
}
