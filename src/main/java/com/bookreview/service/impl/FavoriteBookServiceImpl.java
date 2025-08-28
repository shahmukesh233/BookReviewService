package com.bookreview.service.impl;

import com.bookreview.model.Book;
import com.bookreview.dto.FavoriteBookDTO;
import com.bookreview.model.FavoriteBook;
import com.bookreview.model.User;
import com.bookreview.repository.BookRepository;
import com.bookreview.repository.FavoriteBookRepository;
import com.bookreview.repository.UserRepository;
import com.bookreview.service.FavoriteBookService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FavoriteBookServiceImpl implements FavoriteBookService {
    private FavoriteBookDTO toDTO(FavoriteBook favoriteBook) {
    FavoriteBookDTO dto = new FavoriteBookDTO();
    dto.setId(favoriteBook.getId());
    dto.setUserId(favoriteBook.getUser().getId());
    dto.setBookId(favoriteBook.getBook().getId());
    dto.setBookTitle(favoriteBook.getBook().getTitle());
    dto.setBookAuthor(favoriteBook.getBook().getAuthor());
    dto.setBookGenres(favoriteBook.getBook().getGenres());
    return dto;
    }
    private final FavoriteBookRepository favoriteBookRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public FavoriteBookServiceImpl(FavoriteBookRepository favoriteBookRepository, UserRepository userRepository, BookRepository bookRepository) {
        this.favoriteBookRepository = favoriteBookRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    @Transactional
    public void markFavorite(Long userId, Long bookId) {
        User user = userRepository.findById(userId).orElseThrow();
        Book book = bookRepository.findById(bookId).orElseThrow();
        if (favoriteBookRepository.findByUserAndBook(user, book).isEmpty()) {
            FavoriteBook favorite = FavoriteBook.builder().user(user).book(book).build();
            favoriteBookRepository.save(favorite);
        }
    }

    @Override
    public FavoriteBookDTO getFavorite(Long id) {
        FavoriteBook favorite = favoriteBookRepository.findById(id).orElseThrow();
        return toDTO(favorite);
    }

    @Override
    public java.util.List<FavoriteBookDTO> getFavoritesByUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return favoriteBookRepository.findByUser(user)
            .stream().map(this::toDTO).collect(java.util.stream.Collectors.toList());
    }

    @Override
    public FavoriteBookDTO updateFavorite(Long id, Long userId, Long bookId) {
        FavoriteBook favorite = favoriteBookRepository.findById(id).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();
        Book book = bookRepository.findById(bookId).orElseThrow();
        favorite.setUser(user);
        favorite.setBook(book);
        FavoriteBook updated = favoriteBookRepository.save(favorite);
        return toDTO(updated);
    }

    @Override
    public void deleteFavorite(Long id) {
        favoriteBookRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void unmarkFavorite(Long userId, Long bookId) {
        User user = userRepository.findById(userId).orElseThrow();
        Book book = bookRepository.findById(bookId).orElseThrow();
        favoriteBookRepository.deleteByUserAndBook(user, book);
    }
}
