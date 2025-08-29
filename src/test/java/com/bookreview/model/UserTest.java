package com.bookreview.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Collections;

class UserTest {
    @Test
    void testBuilderAndGettersSetters() {
        User user = User.builder()
                .id(1L)
                .username("user")
                .password("pass")
                .role("ADMIN")
                .firstname("First")
                .lastname("Last")
                .reviews(Collections.emptyList())
                .build();
        assertEquals(1L, user.getId());
        assertEquals("user", user.getUsername());
        assertEquals("pass", user.getPassword());
        assertEquals("ADMIN", user.getRole());
        assertEquals("First", user.getFirstname());
        assertEquals("Last", user.getLastname());
        assertEquals(Collections.emptyList(), user.getReviews());
    }

    @Test
    void testNoArgsConstructor() {
        User user = new User();
        assertNull(user.getId());
    }
}
