package com.bookreview.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    private String description;
    @Column(name = "cover_image")
    private String coverImage;
    @Column(name = "genres")
    private String genres;
    @Column(name = "published_year")
    private String publishedYear;
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<Review> reviews;

    @Column(name = "avg_rating")
    private Double avgRating;

    @Column(name = "review_count")
    private Integer reviewCount;
}
