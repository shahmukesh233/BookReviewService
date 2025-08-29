package com.bookreview.config;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {
    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleIllegalArgument_shouldReturnBadRequest() {
        ResponseEntity<Map<String, Object>> response = handler.handleIllegalArgument(new IllegalArgumentException("bad arg"));
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().get("detail").toString().contains("bad arg"));
    }

    @Test
    void handleNotFound_shouldReturnNotFound() {
        ResponseEntity<Map<String, Object>> response = handler.handleNotFound(new java.util.NoSuchElementException("not found"));
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void handleGeneric_shouldReturnServerError() {
        ResponseEntity<Map<String, Object>> response = handler.handleGeneric(new Exception("error"));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
