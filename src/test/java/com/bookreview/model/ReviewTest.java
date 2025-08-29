package com.bookreview.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;

class ReviewTest {
    @Test
    void testBuilderAndGettersSetters() {
        Review review = Review.builder()
                .id(1L)
                .rating(5)
                .comment("Great")
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();
        assertEquals(1L, review.getId());
        assertEquals(5, review.getRating());
        assertEquals("Great", review.getComment());
        assertNotNull(review.getCreatedAt());
        assertNotNull(review.getModifiedAt());
    }

    @Test
    void testNoArgsConstructor() {
        Review review = new Review();
        assertNull(review.getId());
    }
}
