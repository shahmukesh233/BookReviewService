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
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceImplTest {
    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private ReviewServiceImpl reviewService;

    @Test
    void addReview_shouldSaveReview() {
        ReviewDTO dto = new ReviewDTO();
        dto.setBookId(1L);
        dto.setUserId(1L);
        Book book = Book.builder().id(1L).build();
        User user = User.builder().id(1L).build();
        Review review = Review.builder().id(1L).book(book).user(user).build();
        when(bookRepository.findById(1L)).thenReturn(java.util.Optional.of(book));
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(user));
        when(reviewRepository.save(any(Review.class))).thenReturn(review);
        ReviewDTO result = reviewService.addReview(dto);
        assertEquals(1L, result.getId());
    }
}
