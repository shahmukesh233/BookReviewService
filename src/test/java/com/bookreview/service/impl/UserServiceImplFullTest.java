package com.bookreview.service.impl;

import com.bookreview.dto.UserDTO;
import com.bookreview.model.User;
import com.bookreview.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplFullTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void updateUser_shouldUpdateUser() {
        User user = User.builder().id(1L).build();
        UserDTO dto = new UserDTO();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);
        UserDTO result = userService.updateUser(1L, dto);
        assertEquals(1L, result.getId());
    }

    @Test
    void deleteUser_shouldCallRepository() {
        userService.deleteUser(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void login_shouldReturnUserDTO() {
        User user = User.builder().id(1L).username("user").password(new BCryptPasswordEncoder().encode("pass")).role("ADMIN").firstname("First").lastname("Last").build();
        when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));
        UserDTO result = userService.login("user", "pass");
        assertEquals("user", result.getUsername());
    }

    @Test
    void login_shouldThrowExceptionForInvalidUser() {
        when(userRepository.findByUsername("nouser")).thenReturn(Optional.empty());
        assertThrows(java.util.NoSuchElementException.class, () -> userService.login("nouser", "pass"));
    }

    @Test
    void login_shouldThrowExceptionForInvalidPassword() {
        User user = User.builder().id(1L).username("user").password(new BCryptPasswordEncoder().encode("pass")).role("ADMIN").firstname("First").lastname("Last").build();
        when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));
        assertThrows(IllegalArgumentException.class, () -> userService.login("user", "wrong"));
    }
}
