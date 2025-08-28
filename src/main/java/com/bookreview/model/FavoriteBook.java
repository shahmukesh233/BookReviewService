package com.bookreview.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "favorite_books")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavoriteBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;
}
