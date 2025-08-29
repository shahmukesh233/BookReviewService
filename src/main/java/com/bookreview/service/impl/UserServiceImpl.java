package com.bookreview.service.impl;

import com.bookreview.dto.UserDTO;
import com.bookreview.model.User;
import com.bookreview.repository.UserRepository;
import com.bookreview.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO registerUser(UserDTO userDTO) {
    log.info("[UserServiceImpl] registerUser called for username: {}", userDTO.getUsername());
    User user = User.builder()
        .username(userDTO.getUsername())
        .password(passwordEncoder.encode(userDTO.getPassword()))
        .role(userDTO.getRole())
        .firstname(userDTO.getFirstname())
        .lastname(userDTO.getLastname())
        .build();
        User saved = userRepository.save(user);
        userDTO.setId(saved.getId());
        return userDTO;
    }

    @Override
    public UserDTO getUserById(Long id) {
    log.info("[UserServiceImpl] getUserById called for id: {}", id);
        User user = userRepository.findById(id).orElseThrow();
    UserDTO dto = new UserDTO();
    dto.setId(user.getId());
    dto.setUsername(user.getUsername());
    dto.setRole(user.getRole());
    dto.setFirstname(user.getFirstname());
    dto.setLastname(user.getLastname());
    return dto;
    }

    @Override
    public List<UserDTO> getAllUsers() {
    log.info("[UserServiceImpl] getAllUsers called");
        return userRepository.findAll().stream().map(user -> {
            UserDTO dto = new UserDTO();
            dto.setId(user.getId());
            dto.setUsername(user.getUsername());
            dto.setRole(user.getRole());
            dto.setFirstname(user.getFirstname());
            dto.setLastname(user.getLastname());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
    log.info("[UserServiceImpl] updateUser called for id: {}", id);
        User user = userRepository.findById(id).orElseThrow();
    user.setUsername(userDTO.getUsername());
    user.setRole(userDTO.getRole());
    user.setFirstname(userDTO.getFirstname());
    user.setLastname(userDTO.getLastname());
        User updated = userRepository.save(user);
    userDTO.setId(updated.getId());
    return userDTO;
    }

    @Override
    public void deleteUser(Long id) {
    log.info("[UserServiceImpl] deleteUser called for id: {}", id);
        userRepository.deleteById(id);
    }

    @Override
    public UserDTO login(String username, String password) {
    log.info("[UserServiceImpl] login called for username: {}", username);
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new java.util.NoSuchElementException("User not found"));
        if (passwordEncoder.matches(password, user.getPassword())) {
            UserDTO dto = new UserDTO();
            dto.setId(user.getId());
            dto.setUsername(user.getUsername());
            dto.setRole(user.getRole());
            dto.setFirstname(user.getFirstname());
            dto.setLastname(user.getLastname());
            return dto;
        }
        throw new IllegalArgumentException("Invalid credentials");
    }
}
