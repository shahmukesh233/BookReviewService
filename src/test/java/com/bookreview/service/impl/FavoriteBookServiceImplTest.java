package com.bookreview.service.impl;

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
import static org.mockito.Mockito.*;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class FavoriteBookServiceImplTest {
    @Mock
    private FavoriteBookRepository favoriteBookRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private BookRepository bookRepository;
    @InjectMocks
    private FavoriteBookServiceImpl favoriteBookService;

    @Test
    void markFavorite_shouldSaveFavorite() {
        User user = User.builder().id(1L).build();
        Book book = Book.builder().id(1L).build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(favoriteBookRepository.findByUserAndBook(user, book)).thenReturn(Optional.empty());
        favoriteBookService.markFavorite(1L, 1L);
        verify(favoriteBookRepository, times(1)).save(any(FavoriteBook.class));
    }
}
