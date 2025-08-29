package com.bookreview.service.impl;

import com.bookreview.dto.UserDTO;
import com.bookreview.model.User;
import com.bookreview.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void registerUser_shouldSaveUser() {
        UserDTO dto = new UserDTO();
        dto.setUsername("testuser");
        dto.setPassword("password");
        dto.setRole("USER");
        dto.setFirstname("Test");
        dto.setLastname("User");
        User user = User.builder().username("testuser").password("password").role("USER").firstname("Test").lastname("User").build();
        when(userRepository.save(any(User.class))).thenReturn(user);
        UserDTO result = userService.registerUser(dto);
        assertEquals("testuser", result.getUsername());
    }

    @Test
    void getUserById_shouldReturnUserDTO() {
        User user = User.builder().id(1L).username("testuser").role("USER").firstname("Test").lastname("User").build();
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(user));
        UserDTO result = userService.getUserById(1L);
        assertEquals(1L, result.getId());
        assertEquals("testuser", result.getUsername());
    }
}
