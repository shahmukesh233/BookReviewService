package com.bookreview.service;

import com.bookreview.dto.FavoriteBookDTO;
import java.util.List;

public interface FavoriteBookService {
    void markFavorite(Long userId, Long bookId);
    void unmarkFavorite(Long userId, Long bookId);
    FavoriteBookDTO getFavorite(Long id);
    List<FavoriteBookDTO> getFavoritesByUser(Long userId);
    FavoriteBookDTO updateFavorite(Long id, Long userId, Long bookId);
    void deleteFavorite(Long id);
}
