package com.encore.board.author.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

//DTO를 통해서 사용자가 데이터를 넘길 예정!
@AllArgsConstructor
@Data
public class AuthorSaveReqDto {
    private String name;
    private String email;
    private String password;
}
