package com.bookreview.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserDTOTest {
    @Test
    void testGettersSetters() {
        UserDTO dto = new UserDTO();
        dto.setId(1L);
        dto.setUsername("user");
        dto.setPassword("pass");
        dto.setRole("ADMIN");
        dto.setFirstname("First");
        dto.setLastname("Last");
        assertEquals(1L, dto.getId());
        assertEquals("user", dto.getUsername());
        assertEquals("pass", dto.getPassword());
        assertEquals("ADMIN", dto.getRole());
        assertEquals("First", dto.getFirstname());
        assertEquals("Last", dto.getLastname());
    }
}
