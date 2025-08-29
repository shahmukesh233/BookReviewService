package com.bookreview.controller;

import com.bookreview.dto.FavoriteBookDTO;
import com.bookreview.service.FavoriteBookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
