package com.encore.board.author.service;

import com.encore.board.author.domain.Author;
import com.encore.board.author.dto.AuthorDetailResDto;
import com.encore.board.author.dto.AuthorListResDto;
import com.encore.board.author.dto.AuthorSaveReqDto;
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
        Author author = new Author(authorSaveReqDto.getName(),
                authorSaveReqDto.getEmail(),
                authorSaveReqDto.getPassword());
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
        Author author = authorRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("일치하는 ID의 회원이 없어요!"));
        AuthorDetailResDto authorDetailResDto = new AuthorDetailResDto(author.getId(), author.getName(),
                author.getEmail(), author.getPassword(), author.getCreatedTime());
        return authorDetailResDto;
    }
}
