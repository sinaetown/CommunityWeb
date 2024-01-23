package com.encore.board.post.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PostDetailResDto {
    private Long id;
    private String title;
    private String contents;
    private LocalDateTime createdTime;
}
