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
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AuthorService {
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public void save(AuthorSaveReqDto authorSaveReqDto) {
        Author.Role role = null;
        if (authorSaveReqDto.getRole() == null || authorSaveReqDto.getRole().equals("user")) {
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
                .role(role)
                .build();

//        Cascade.PERSIST 옵션 테스트
//        부모테이블을 통해 자식테이블에 객체를 동시에 생성
//        List<Post> posts = new ArrayList<>();
//        Post post = Post.builder()
//                .title(author.getName() + "님 회원가입을 축하드립니다! :D")
//                .contents("반갑습니다!")
//                .author(author)
//                .build();
//
//        posts.add(post);
//        author.setPosts(posts);
        authorRepository.save(author);
    }

    public List<AuthorListResDto> findAll() {
        List<AuthorListResDto> authorListResDtos = new ArrayList<>();
        for (Author author : authorRepository.findAll()) {

            AuthorListResDto authorListResDto = AuthorListResDto.builder()
                    .id(author.getId())
                    .name(author.getName())
                    .email(author.getEmail()).build();
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
        AuthorDetailResDto authorDetailResDto = AuthorDetailResDto.builder()
                .id(author.getId())
                .name(author.getName())
                .email(author.getEmail())
                .password(author.getPassword())
                .role(role)
                .counts(author.getPosts().size())
                .createdTime(author.getCreatedTime())
                .build();
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
//        명시적으로 save를 하지 않더라도, JPA의 영속성 컨텍스트를 통해 객체에 변경이 감지되면 (dirty check)
//       트랜잭션이 완료되는 시점에 save가 동작한다
//        authorRepository.save(author);
    }

    public void delete(Long id) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("일치하는 ID의 회원이 없어요!"));
        authorRepository.delete(author);
    }
}
