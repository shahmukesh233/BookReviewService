package com.bookreview.service.impl;

import com.bookreview.dto.ReviewDTO;
import com.bookreview.model.Book;
import com.bookreview.model.Review;
import com.bookreview.model.User;
import com.bookreview.repository.BookRepository;
import com.bookreview.repository.ReviewRepository;
import com.bookreview.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Collections;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class ReviewServiceImplFullTest {
    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private ReviewServiceImpl reviewService;

    @Test
    void updateReview_shouldUpdateReview() {
        Review review = Review.builder().id(1L).build();
        ReviewDTO dto = new ReviewDTO();
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));
        when(reviewRepository.save(any(Review.class))).thenReturn(review);
        ReviewDTO result = reviewService.updateReview(1L, dto);
        assertEquals(1L, result.getId());
    }

    @Test
    void deleteReview_shouldDeleteAndUpdateBook() {
        Review review = Review.builder().id(1L).book(Book.builder().id(2L).build()).build();
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));
        reviewService.deleteReview(1L);
        verify(reviewRepository, times(1)).deleteById(1L);
    }

    @Test
    void getReviewsByBookId_shouldReturnList() {
        Book book = Book.builder().id(1L).build();
        Review review = Review.builder().id(1L).book(book).user(User.builder().id(2L).build()).build();
        when(reviewRepository.findAll()).thenReturn(Collections.singletonList(review));
        List<ReviewDTO> result = reviewService.getReviewsByBookId(1L);
        assertEquals(1, result.size());
    }

    @Test
    void getReviewsByUserId_shouldReturnList() {
        User user = User.builder().id(2L).build();
        Review review = Review.builder().id(1L).book(Book.builder().id(1L).build()).user(user).build();
        when(reviewRepository.findAll()).thenReturn(Collections.singletonList(review));
        List<ReviewDTO> result = reviewService.getReviewsByUserId(2L);
        assertEquals(1, result.size());
    }

    @Test
    void getAverageRatingForBook_shouldReturnAverage() {
        Book book = Book.builder().id(1L).build();
        Review review = Review.builder().id(1L).book(book).rating(5).build();
        when(reviewRepository.findAll()).thenReturn(Collections.singletonList(review));
        double avg = reviewService.getAverageRatingForBook(1L);
        assertEquals(5.0, avg);
    }
}
