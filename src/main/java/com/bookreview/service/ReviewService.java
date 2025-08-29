package com.bookreview.service;

import com.bookreview.dto.ReviewDTO;
import java.util.List;

public interface ReviewService {
    ReviewDTO addReview(ReviewDTO reviewDTO);
    ReviewDTO updateReview(Long id, ReviewDTO reviewDTO);
    void deleteReview(Long id);
    List<ReviewDTO> getReviewsByBookId(Long bookId);
    List<ReviewDTO> getReviewsByUserId(Long userId);
    double getAverageRatingForBook(Long bookId);
    List<ReviewDTO> getAllReviews();
}
