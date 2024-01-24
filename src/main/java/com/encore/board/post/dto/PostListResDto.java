package com.encore.board.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PostListResDto {
    private Long id;
    private String title;
    private String author_email;
}

