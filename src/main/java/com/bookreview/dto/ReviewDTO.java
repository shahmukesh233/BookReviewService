package com.bookreview.dto;

import lombok.Data;

@Data
public class ReviewDTO {
    private Long id;
    private int rating;
    private String comment;
    private Long bookId;
    private Long userId;
    private java.time.LocalDateTime createdAt;
    private java.time.LocalDateTime modifiedAt;
    private String bookTitle;
}
