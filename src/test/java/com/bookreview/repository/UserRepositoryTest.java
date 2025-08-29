package com.bookreview.repository;

import com.bookreview.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void saveAndFindUser() {
        User user = User.builder().username("testuser").password("password").role("USER").firstname("Test").lastname("User").build();
        User saved = userRepository.save(user);
        assertNotNull(saved.getId());
        User found = userRepository.findById(saved.getId()).orElse(null);
        assertNotNull(found);
        assertEquals("testuser", found.getUsername());
    }
}
