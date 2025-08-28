package com.bookreview.repository;

import com.bookreview.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Logger log = LoggerFactory.getLogger(ReviewRepository.class);
    // For custom queries, add log statements in the implementation if needed.
}
