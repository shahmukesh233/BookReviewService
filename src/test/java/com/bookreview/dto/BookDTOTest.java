package com.bookreview.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Collections;

class BookDTOTest {
    @Test
    void testGettersSetters() {
        BookDTO dto = new BookDTO();
        dto.setId(1L);
        dto.setTitle("Title");
        dto.setAuthor("Author");
        dto.setDescription("Desc");
        dto.setCoverImage("img.png");
        dto.setGenres("Fiction");
        dto.setPublishedYear("2020");
        dto.setAvgRating(4.5);
        dto.setReviewCount(10);
        dto.setReviews(Collections.emptyList());
        assertEquals(1L, dto.getId());
        assertEquals("Title", dto.getTitle());
        assertEquals("Author", dto.getAuthor());
        assertEquals("Desc", dto.getDescription());
        assertEquals("img.png", dto.getCoverImage());
        assertEquals("Fiction", dto.getGenres());
        assertEquals("2020", dto.getPublishedYear());
        assertEquals(4.5, dto.getAvgRating());
        assertEquals(10, dto.getReviewCount());
        assertEquals(Collections.emptyList(), dto.getReviews());
    }
}
