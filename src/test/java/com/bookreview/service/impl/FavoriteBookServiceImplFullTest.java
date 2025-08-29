package com.bookreview.service.impl;

import com.bookreview.dto.FavoriteBookDTO;
import com.bookreview.model.Book;
import com.bookreview.model.FavoriteBook;
import com.bookreview.model.User;
import com.bookreview.repository.BookRepository;
import com.bookreview.repository.FavoriteBookRepository;
import com.bookreview.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FavoriteBookServiceImplFullTest {
    @Mock
    private FavoriteBookRepository favoriteBookRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private BookRepository bookRepository;
    @InjectMocks
    private FavoriteBookServiceImpl favoriteBookService;

    @Test
    void updateFavorite_shouldUpdateFavorite() {
        FavoriteBook favorite = FavoriteBook.builder().id(1L).build();
        User user = User.builder().id(2L).build();
        Book book = Book.builder().id(3L).build();
        when(favoriteBookRepository.findById(1L)).thenReturn(Optional.of(favorite));
        when(userRepository.findById(2L)).thenReturn(Optional.of(user));
        when(bookRepository.findById(3L)).thenReturn(Optional.of(book));
        when(favoriteBookRepository.save(any(FavoriteBook.class))).thenReturn(favorite);
        FavoriteBookDTO result = favoriteBookService.updateFavorite(1L, 2L, 3L);
        assertEquals(1L, result.getId());
    }

    @Test
    void deleteFavorite_shouldCallRepository() {
        favoriteBookService.deleteFavorite(1L);
        verify(favoriteBookRepository, times(1)).deleteById(1L);
    }

    @Test
    void unmarkFavorite_shouldDeleteByUserAndBook() {
        User user = User.builder().id(2L).build();
        Book book = Book.builder().id(3L).build();
        when(userRepository.findById(2L)).thenReturn(Optional.of(user));
        when(bookRepository.findById(3L)).thenReturn(Optional.of(book));
        favoriteBookService.unmarkFavorite(2L, 3L);
        verify(favoriteBookRepository, times(1)).deleteByUserAndBook(user, book);
    }
}
