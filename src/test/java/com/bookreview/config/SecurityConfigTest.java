package com.bookreview.config;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import static org.junit.jupiter.api.Assertions.*;

class SecurityConfigTest {
    @Test
    void passwordEncoder_shouldReturnBCryptPasswordEncoder() {
        SecurityConfig config = new SecurityConfig(null);
        PasswordEncoder encoder = config.passwordEncoder();
        assertTrue(encoder instanceof BCryptPasswordEncoder);
    }

    @Test
    void corsConfigurer_shouldReturnWebMvcConfigurer() {
        SecurityConfig config = new SecurityConfig(null);
        WebMvcConfigurer cors = config.corsConfigurer();
        assertNotNull(cors);
    }
}
