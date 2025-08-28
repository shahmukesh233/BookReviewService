package com.bookreview.dto;

import lombok.Data;

@Data
public class FavoriteBookDTO {
    private Long id;
    private Long userId;
    private Long bookId;
    private String bookTitle;
    private String bookAuthor;
    private String bookGenres;
}
