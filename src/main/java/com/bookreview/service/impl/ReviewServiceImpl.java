package com.bookreview.service.impl;

import com.bookreview.dto.ReviewDTO;
import com.bookreview.model.Book;
import com.bookreview.model.Review;
import com.bookreview.model.User;
import com.bookreview.repository.BookRepository;
import com.bookreview.repository.ReviewRepository;
import com.bookreview.repository.UserRepository;
import com.bookreview.service.ReviewService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {
    // ThreadLocal to hold the token for each request
    private static final ThreadLocal<String> currentAuthToken = new ThreadLocal<>();

    // Call this at the start of each request (e.g., in a filter or controller) to set the token
    public static void setCurrentAuthToken(String token) {
        currentAuthToken.set(token);
    }

    // Call this at the end of each request to clear the token
    public static void clearCurrentAuthToken() {
        currentAuthToken.remove();
    }
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ReviewServiceImpl.class);
    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository, BookRepository bookRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ReviewDTO addReview(ReviewDTO reviewDTO) {
        log.info("[ReviewServiceImpl] addReview called for bookId: {}, userId: {}", reviewDTO.getBookId(), reviewDTO.getUserId());
        Book book = bookRepository.findById(reviewDTO.getBookId())
            .orElseThrow(() -> new IllegalArgumentException("Book not found with id: " + reviewDTO.getBookId()));
        User user = userRepository.findById(reviewDTO.getUserId())
            .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + reviewDTO.getUserId()));
        Review review = Review.builder()
                .rating(reviewDTO.getRating())
                .comment(reviewDTO.getComment())
                .book(book)
                .user(user)
                .createdAt(java.time.LocalDateTime.now())
                .modifiedAt(java.time.LocalDateTime.now())
                .build();
        Review saved = reviewRepository.save(review);
        reviewDTO.setId(saved.getId());
        reviewDTO.setCreatedAt(saved.getCreatedAt());
        reviewDTO.setModifiedAt(saved.getModifiedAt());
        updateBookRatingAndCount(reviewDTO.getBookId());
        return reviewDTO;
    }

    @Override
    public ReviewDTO updateReview(Long id, ReviewDTO reviewDTO) {
        log.info("[ReviewServiceImpl] updateReview called for id: {}", id);
        Review review = reviewRepository.findById(id).orElseThrow();
        review.setRating(reviewDTO.getRating());
        review.setComment(reviewDTO.getComment());
        review.setModifiedAt(java.time.LocalDateTime.now());
        Review updated = reviewRepository.save(review);
        reviewDTO.setId(updated.getId());
        reviewDTO.setCreatedAt(updated.getCreatedAt());
        reviewDTO.setModifiedAt(updated.getModifiedAt());
        updateBookRatingAndCount(updated.getBook().getId());
        return reviewDTO;
    }

    @Override
    public void deleteReview(Long id) {
        log.info("[ReviewServiceImpl] deleteReview called for id: {}", id);
        Review review = reviewRepository.findById(id).orElse(null);
        Long bookId = (review != null && review.getBook() != null) ? review.getBook().getId() : null;
        reviewRepository.deleteById(id);
        if (bookId != null) {
            updateBookRatingAndCount(bookId);
        }
    }

    private void updateBookRatingAndCount(Long bookId) {
    List<Review> reviews = reviewRepository.findAll().stream()
        .filter(r -> r.getBook().getId().equals(bookId))
        .collect(Collectors.toList());
    int reviewCount = reviews.size();
    double avgRating = reviewCount > 0 ? Math.round(reviews.stream().mapToInt(Review::getRating).average().orElse(0.0) * 10.0) / 10.0 : 0.0;
    // Directly call BookServiceImpl to update avgRating and reviewCount
    bookRepository.findById(bookId).ifPresent(book -> {
        book.setAvgRating(avgRating);
        book.setReviewCount(reviewCount);
        bookRepository.save(book);
    });
    }

    @Override
    public List<ReviewDTO> getReviewsByBookId(Long bookId) {
        log.info("[ReviewServiceImpl] getReviewsByBookId called for bookId: {}", bookId);
        return reviewRepository.findAll().stream()
                .filter(r -> r.getBook().getId().equals(bookId))
                .map(r -> {
                    ReviewDTO dto = new ReviewDTO();
                    dto.setId(r.getId());
                    dto.setRating(r.getRating());
                    dto.setComment(r.getComment());
                    dto.setBookId(r.getBook().getId());
                    dto.setUserId(r.getUser().getId());
                    dto.setCreatedAt(r.getCreatedAt());
                    dto.setModifiedAt(r.getModifiedAt());
                    dto.setBookTitle(r.getBook().getTitle());
                    return dto;
                }).collect(Collectors.toList());
    }

    @Override
    public List<ReviewDTO> getReviewsByUserId(Long userId) {
        log.info("[ReviewServiceImpl] getReviewsByUserId called for userId: {}", userId);
        return reviewRepository.findAll().stream()
                .filter(r -> r.getUser().getId().equals(userId))
                .map(r -> {
                    ReviewDTO dto = new ReviewDTO();
                    dto.setId(r.getId());
                    dto.setRating(r.getRating());
                    dto.setComment(r.getComment());
                    dto.setBookId(r.getBook().getId());
                    dto.setUserId(r.getUser().getId());
                    dto.setCreatedAt(r.getCreatedAt());
                    dto.setModifiedAt(r.getModifiedAt());
                    dto.setBookTitle(r.getBook().getTitle());
                    return dto;
                }).collect(Collectors.toList());
    }

    @Override
    public double getAverageRatingForBook(Long bookId) {
        log.info("[ReviewServiceImpl] getAverageRatingForBook called for bookId: {}", bookId);
        List<Review> reviews = reviewRepository.findAll().stream()
                .filter(r -> r.getBook().getId().equals(bookId))
                .collect(Collectors.toList());
        return reviews.stream().mapToInt(Review::getRating).average().orElse(0.0);
    }

}

