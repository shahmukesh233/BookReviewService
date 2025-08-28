package com.bookreview.controller;

import com.bookreview.dto.FavoriteBookDTO;
import com.bookreview.service.FavoriteBookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteBookCrudController {
    private final FavoriteBookService favoriteBookService;

    public FavoriteBookCrudController(FavoriteBookService favoriteBookService) {
        this.favoriteBookService = favoriteBookService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<FavoriteBookDTO> getFavorite(@PathVariable Long id) {
        return ResponseEntity.ok(favoriteBookService.getFavorite(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<FavoriteBookDTO>> getFavoritesByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(favoriteBookService.getFavoritesByUser(userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FavoriteBookDTO> updateFavorite(@PathVariable Long id, @RequestBody FavoriteBookDTO dto) {
        FavoriteBookDTO updated = favoriteBookService.updateFavorite(id, dto.getUserId(), dto.getBookId());
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFavorite(@PathVariable Long id) {
        favoriteBookService.deleteFavorite(id);
        return ResponseEntity.ok().build();
    }
}
