package com.bookreview.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Collections;

class BookTest {
    @Test
    void testBuilderAndGettersSetters() {
        Book book = Book.builder()
                .id(1L)
                .title("Title")
                .author("Author")
                .description("Desc")
                .coverImage("img.png")
                .genres("Fiction")
                .publishedYear("2020")
                .avgRating(4.5)
                .reviewCount(10)
                .reviews(Collections.emptyList())
                .build();
        assertEquals(1L, book.getId());
        assertEquals("Title", book.getTitle());
        assertEquals("Author", book.getAuthor());
        assertEquals("Desc", book.getDescription());
        assertEquals("img.png", book.getCoverImage());
        assertEquals("Fiction", book.getGenres());
        assertEquals("2020", book.getPublishedYear());
        assertEquals(4.5, book.getAvgRating());
        assertEquals(10, book.getReviewCount());
        assertEquals(Collections.emptyList(), book.getReviews());
    }

    @Test
    void testNoArgsConstructor() {
        Book book = new Book();
        assertNull(book.getId());
    }
}
