package com.encore.board.author.dto;

import lombok.Data;

@Data
public class AuthorUpdateReqDto {
    private Long id;
    private String name;
    private String password;
    private String role;
}
