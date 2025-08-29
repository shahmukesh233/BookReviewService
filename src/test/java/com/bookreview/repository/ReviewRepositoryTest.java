package com.bookreview.repository;

import com.bookreview.model.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
class ReviewRepositoryTest {
    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    void saveAndFindReview() {
        Review review = Review.builder().rating(5).comment("Great!").build();
        Review saved = reviewRepository.save(review);
        assertNotNull(saved.getId());
        Review found = reviewRepository.findById(saved.getId()).orElse(null);
        assertNotNull(found);
        assertEquals(5, found.getRating());
    }
}
