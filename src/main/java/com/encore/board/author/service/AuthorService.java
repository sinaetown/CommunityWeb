package com.encore.board.author.service;

import com.encore.board.author.domain.Author;
import com.encore.board.author.dto.AuthorDetailResDto;
import com.encore.board.author.dto.AuthorListResDto;
import com.encore.board.author.dto.AuthorSaveReqDto;
import com.encore.board.author.dto.AuthorUpdateReqDto;
import com.encore.board.author.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public void save(AuthorSaveReqDto authorSaveReqDto) {
        Author.Role role = null;
        if (authorSaveReqDto.getRole() == null ||authorSaveReqDto.getRole().equals("user")) {
            role = Author.Role.USER;
        } else {
            role = Author.Role.ADMIN;
        }
//        일반 생성자 방식
//        Author author = new Author(authorSaveReqDto.getName(),
//                authorSaveReqDto.getEmail(),
//                authorSaveReqDto.getPassword(),
//                role);

//        Builder 패턴
        Author author = Author.builder()
                .name(authorSaveReqDto.getName())
                .email(authorSaveReqDto.getEmail())
                .password(authorSaveReqDto.getPassword())
                .build();
        authorRepository.save(author);
    }

    public List<AuthorListResDto> findAll() {
        List<AuthorListResDto> authorListResDtos = new ArrayList<>();
        for (Author author : authorRepository.findAll()) {
            AuthorListResDto authorListResDto = new AuthorListResDto(author.getId(), author.getName(), author.getEmail());
            authorListResDtos.add(authorListResDto);
        }
        return authorListResDtos;
    }

    public AuthorDetailResDto findById(Long id) throws EntityNotFoundException {
        Author author = authorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("일치하는 ID의 회원이 없어요!"));
        String role = null;
        if (author.getRole() == null || author.getRole().equals("user")) {
            role = "일반 유저";
        } else {
            role = "관리자";
        }
        AuthorDetailResDto authorDetailResDto = new AuthorDetailResDto(author.getId(), author.getName(),
                author.getEmail(), author.getPassword(), role, author.getCreatedTime());
        return authorDetailResDto;
    }


    public void update(AuthorUpdateReqDto authorUpdateReqDto) {
        Author author = authorRepository.findById(authorUpdateReqDto.getId()).orElseThrow(() -> new EntityNotFoundException("일치하는 ID의 회원이 없어요!"));
        Author.Role role = null;
        if (authorUpdateReqDto.getRole().equals("ADMIN")) {
            role = Author.Role.ADMIN;
        } else {
            role = Author.Role.USER;
        }
        author.update(authorUpdateReqDto.getName(), authorUpdateReqDto.getPassword(), role);
        authorRepository.save(author);
    }

    public void delete(Long id) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("일치하는 ID의 회원이 없어요!"));
        authorRepository.delete(author);
    }
}
