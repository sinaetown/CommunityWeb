package com.encore.board.author.service;

import com.encore.board.author.domain.Author;
import com.encore.board.author.dto.AuthorUpdateReqDto;
import com.encore.board.author.repository.AuthorRepository;
import com.encore.board.post.domain.Post;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class AuthorServiceTest {

    @Autowired
    private AuthorService authorService;

    //    가짜 객체를 만드는 작업을 mocking이라고 함
    @MockBean
    private AuthorRepository authorRepository;

    @Test
    void findAllTest() {
//        Mock repository 기능 구현
        List<Author> authors = new ArrayList<>();
        authors.add(new Author());
        authors.add(new Author());
        Mockito.when(authorRepository.findAll()).thenReturn(authors);
//        검증
        Assertions.assertEquals(2, authorService.findAll().size());
    }

    @Test
    void updateTest() {
//        Mock repository 기능 구현
        Long author_id = 1L;
        Author author = Author.builder().name("First").email("first@naver.com").password("1234").build();
        Mockito.when(authorRepository.findById(author_id)).thenReturn(Optional.of(author));
        AuthorUpdateReqDto authorUpdateReqDto = new AuthorUpdateReqDto();
        authorUpdateReqDto.setId(author_id);
        authorUpdateReqDto.setName("Second");
        authorUpdateReqDto.setPassword("5678");
        authorUpdateReqDto.setRole("admin");
//        검증
        authorService.update(authorUpdateReqDto);
        Assertions.assertEquals(author.getName(), authorUpdateReqDto.getName());
        Assertions.assertEquals(author.getPassword(), authorUpdateReqDto.getPassword());
    }

    @Test
    void findDetailTest(){
        Long author_id = 1L;
        List<Post> posts = new ArrayList<>();
        posts.add(new Post());
        posts.add(new Post());
        Author author = Author.builder().name("First").email("first@naver.com").password("1234").posts(posts).build();
        Mockito.when(authorRepository.findById(author_id)).thenReturn(Optional.of(author));
        Assertions.assertEquals(author.getName(), authorService.findAuthorDetail(author_id).getName());
        Assertions.assertEquals(author.getEmail(), authorService.findAuthorDetail(author_id).getEmail());
        Assertions.assertEquals(author.getPosts().size(), authorService.findAuthorDetail(author_id).getCounts());
    }

}
