package com.bookreview.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FavoriteBookTest {
    @Test
    void testBuilderAndGettersSetters() {
        FavoriteBook fav = FavoriteBook.builder()
                .id(1L)
                .build();
        assertEquals(1L, fav.getId());
    }

    @Test
    void testNoArgsConstructor() {
        FavoriteBook fav = new FavoriteBook();
        assertNull(fav.getId());
    }
}
