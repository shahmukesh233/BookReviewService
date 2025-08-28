package com.bookreview.controller;

import com.bookreview.dto.FavoriteBookDTO;
import com.bookreview.service.FavoriteBookService;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/users/{userId}/favorites")
public class FavoriteBookController {
    private final FavoriteBookService favoriteBookService;

    public FavoriteBookController(FavoriteBookService favoriteBookService) {
        this.favoriteBookService = favoriteBookService;
    }

    @PostMapping
    public ResponseEntity<Void> markFavorite(@PathVariable Long userId, @RequestBody FavoriteRequest request) {
        favoriteBookService.markFavorite(userId, request.getBookId());
        log.info("[FavoriteBookController] markFavorite called for userId: {}, bookId: {}", userId, request.getBookId());
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<FavoriteBookDTO>> getFavoritesByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(favoriteBookService.getFavoritesByUser(userId));
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<Void> unmarkFavorite(@PathVariable Long userId, @PathVariable Long bookId) {
        log.info("[FavoriteBookController] unmarkFavorite called for userId: {}, bookId: {}", userId, bookId);
        favoriteBookService.unmarkFavorite(userId, bookId);
        return ResponseEntity.ok().build();
    }

    public static class FavoriteRequest {
        private Long bookId;
        public Long getBookId() { return bookId; }
        public void setBookId(Long bookId) { this.bookId = bookId; }
    }
}
