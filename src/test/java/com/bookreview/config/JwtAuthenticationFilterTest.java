package com.bookreview.config;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import jakarta.servlet.FilterChain;
import static org.junit.jupiter.api.Assertions.*;

class JwtAuthenticationFilterTest {
    @Test
    void doFilterInternal_shouldNotAuthenticateWithoutToken() throws Exception {
        JwtUtil jwtUtil = Mockito.mock(JwtUtil.class);
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtUtil);
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = Mockito.mock(FilterChain.class);
        filter.doFilterInternal(request, response, chain);
        assertNull(org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication());
    }
}
