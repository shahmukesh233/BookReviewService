package com.bookreview.repository;

import com.bookreview.model.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Test
    void saveAndFindBook() {
        Book book = Book.builder().title("Title").author("Author").build();
        Book saved = bookRepository.save(book);
        assertNotNull(saved.getId());
        Page<Book> page = bookRepository.findAll(PageRequest.of(0, 1));
        assertTrue(page.getTotalElements() > 0);
    }
}
