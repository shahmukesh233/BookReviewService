package com.bookreview.controller;

import com.bookreview.dto.UserDTO;
import com.bookreview.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    @Mock
    private UserService userService;
    @InjectMocks
    private UserController userController;

    @Test
    void registerUser_shouldReturnUserDTO() {
        UserDTO dto = new UserDTO();
        when(userService.registerUser(any(UserDTO.class))).thenReturn(dto);
    UserDTO response = userController.registerUser(dto);
    assertEquals(dto, response);
    }
}
