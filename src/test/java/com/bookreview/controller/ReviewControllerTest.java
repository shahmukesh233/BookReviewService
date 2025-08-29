package com.bookreview.controller;

import com.bookreview.dto.ReviewDTO;
import com.bookreview.service.ReviewService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Collections;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ReviewControllerTest {
    @Mock
    private ReviewService reviewService;
    @InjectMocks
    private ReviewController reviewController;

    @Test
    void addReview_shouldReturnReviewDTO() {
        ReviewDTO dto = new ReviewDTO();
        when(reviewService.addReview(any(ReviewDTO.class))).thenReturn(dto);
        ReviewDTO result = reviewController.addReview(dto);
        assertEquals(dto, result);
    }

    @Test
    void getReviewsByBookId_shouldReturnList() {
        when(reviewService.getReviewsByBookId(anyLong())).thenReturn(Collections.emptyList());
        List<ReviewDTO> result = reviewController.getReviewsByBookId(1L);
        assertTrue(result.isEmpty());
    }
}
