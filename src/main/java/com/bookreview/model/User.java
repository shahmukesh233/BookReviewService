package com.bookreview.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String role;
    private String firstname;
    private String lastname;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Review> reviews;
}
