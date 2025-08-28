package com.bookreview.dto;

import lombok.Data;
import java.util.List;

@Data
public class BookDTO {
    private Long id;
    private String title;
    private String author;
    private String description;
    private String coverImage;
    private String genres;
    private String publishedYear;
    private List<ReviewDTO> reviews;
    private Double avgRating;
    private Integer reviewCount;
}
