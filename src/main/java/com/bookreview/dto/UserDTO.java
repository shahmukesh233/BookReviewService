package com.bookreview.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private String role;
    private String firstname;
    private String lastname;
}
