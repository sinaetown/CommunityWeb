package com.encore.board.author.dto;

import lombok.Data;

@Data
public class AuthorListResDto {
    private Long id;
    private String name;
    private String email;

    public AuthorListResDto(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}

