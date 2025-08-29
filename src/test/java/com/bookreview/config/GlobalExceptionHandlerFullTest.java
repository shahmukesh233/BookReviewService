package com.bookreview.config;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerFullTest {
    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleValidation_shouldReturnBadRequest() {
        ResponseEntity<Map<String, Object>> response = handler.handleValidation(new org.springframework.web.bind.MethodArgumentNotValidException(null, null));
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
