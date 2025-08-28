package com.bookreview.controller;

import com.bookreview.service.ReviewService;

import org.springframework.web.bind.annotation.*;
import com.bookreview.dto.ReviewDTO;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ReviewController.class);
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ReviewDTO addReview(@RequestBody ReviewDTO reviewDTO) {
        log.info("[ReviewController] addReview called for bookId: {}, userId: {}", reviewDTO.getBookId(),
                reviewDTO.getUserId());
        return reviewService.addReview(reviewDTO);
    }

    @PutMapping("/{id}")
    public ReviewDTO updateReview(@PathVariable Long id, @RequestBody ReviewDTO reviewDTO) {
        log.info("[ReviewController] updateReview called for id: {}", id);
        return reviewService.updateReview(id, reviewDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable Long id) {
        log.info("[ReviewController] deleteReview called for id: {}", id);
        reviewService.deleteReview(id);
    }

    @GetMapping("/book/{bookId}")
    public List<ReviewDTO> getReviewsByBookId(@PathVariable Long bookId) {
        log.info("[ReviewController] getReviewsByBookId called for bookId: {}", bookId);
        return reviewService.getReviewsByBookId(bookId);
    }

    @GetMapping("/user/{userId}")
    public List<ReviewDTO> getReviewsByUserId(@PathVariable Long userId) {
        log.info("[ReviewController] getReviewsByUserId called for userId: {}", userId);
        return reviewService.getReviewsByUserId(userId);
    }
}
