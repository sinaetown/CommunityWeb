package com.encore.board.author.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AuthorDetailResDto {
    private Long id;
    private String name;
    private String email;
    private String password;
    private LocalDateTime createdTime;
}
