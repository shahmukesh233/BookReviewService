package com.bookreview.controller;

import java.util.Map;
import java.util.HashMap;
import com.bookreview.config.JwtUtil;

import com.bookreview.service.UserService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.bookreview.dto.UserDTO;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public UserDTO registerUser(@RequestBody UserDTO userDTO) {
        log.info("[UserController] registerUser called for username: {}", userDTO.getUsername());
        return userService.registerUser(userDTO);
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestParam String username, @RequestParam String password) {
        log.info("[UserController] login called for username: {}", username);
        UserDTO user = userService.login(username, password);
        String token = jwtUtil.generateToken(user.getUsername(), user.getRole());
        Map<String, Object> response = new HashMap<>();
        response.put("user", user);
        response.put("token", token);
        return response;
    }

    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Long id) {
        log.info("[UserController] getUserById called for id: {}", id);
        return userService.getUserById(id);
    }

    @GetMapping
    public List<UserDTO> getAllUsers() {
        log.info("[UserController] getAllUsers called");
        return userService.getAllUsers();
    }

    @PutMapping("/{id}")
    public UserDTO updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        log.info("[UserController] updateUser called for id: {}", id);
        return userService.updateUser(id, userDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        log.info("[UserController] deleteUser called for id: {}", id);
        userService.deleteUser(id);
    }

    @PostMapping("/logout")
    public Map<String, String> logout() {
        log.info("[UserController] logout called");
        // For JWT, logout is handled client-side by deleting the token. Optionally, you can implement token blacklist.
        Map<String, String> response = new HashMap<>();
        response.put("message", "Logout successful. Please delete your token on the client side.");
        return response;
    }
}
