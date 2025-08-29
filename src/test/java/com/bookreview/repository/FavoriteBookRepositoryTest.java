package com.bookreview.repository;

import com.bookreview.model.FavoriteBook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
class FavoriteBookRepositoryTest {
    @Autowired
    private FavoriteBookRepository favoriteBookRepository;

    @Test
    void saveAndFindFavoriteBook() {
        FavoriteBook favorite = FavoriteBook.builder().build();
        FavoriteBook saved = favoriteBookRepository.save(favorite);
        assertNotNull(saved.getId());
        FavoriteBook found = favoriteBookRepository.findById(saved.getId()).orElse(null);
        assertNotNull(found);
    }
}
