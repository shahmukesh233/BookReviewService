package com.bookreview.config;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {
    @Test
    void generateAndValidateToken() {
        JwtUtil util = new JwtUtil();
        ReflectionTestUtils.setField(util, "secretKey", "VGhpcyBpcyBhIHZlcnkgc2VjdXJlIHNlY3JldCBrZXkgZm9yIGp3dA==");
        String token = util.generateToken("user", "USER");
        assertTrue(util.isTokenValid(token));
        assertEquals("user", util.extractUsername(token));
        assertEquals("USER", util.extractRole(token));
    }
}
