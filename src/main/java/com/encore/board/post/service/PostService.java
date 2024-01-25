package com.encore.board.post.service;

import com.encore.board.author.domain.Author;
import com.encore.board.author.repository.AuthorRepository;
import com.encore.board.post.domain.Post;
import com.encore.board.post.dto.PostDetailResDto;
import com.encore.board.post.dto.PostListResDto;
import com.encore.board.post.dto.PostSaveReqDto;
import com.encore.board.post.dto.PostUpdateReqDto;
import com.encore.board.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public PostService(PostRepository postRepository, AuthorRepository authorRepository) {
        this.postRepository = postRepository;
        this.authorRepository = authorRepository;
    }

    public void save(PostSaveReqDto postSaveReqDto) {
        Author author = authorRepository.findByEmail(postSaveReqDto.getEmail()).orElse(null);
        Post post = Post.builder()
                .title(postSaveReqDto.getTitle())
                .contents(postSaveReqDto.getContents())
                .author(author)
                .build();
//        Dirty Checking 테스트
        author.update("Dirty checking test", "1234", Author.Role.ADMIN);

        postRepository.save(post);
    }

    public List<PostListResDto> findAll() {
        List<PostListResDto> postListResDtos = new ArrayList<>();
        for (Post p : postRepository.findAllFetchJoin()) {
            PostListResDto postListResDto = PostListResDto.builder()
                    .id(p.getId())
                    .title(p.getTitle())
                    .author_email(p.getAuthor()==null ? "익명 유저" : p.getAuthor().getEmail())
                    .build();
            postListResDtos.add(postListResDto);
        }
        return postListResDtos;
    }

    public PostDetailResDto findById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("일치하는 ID의 게시글이 없어요!"));
        PostDetailResDto postDetailResDto = PostDetailResDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .contents(post.getContents())
                .createdTime(post.getCreatedTime()).build();
        return postDetailResDto;
    }

    public void update(PostUpdateReqDto postUpdateReqDto) {
        Post post = postRepository.findById(postUpdateReqDto.getId()).orElseThrow(() -> new EntityNotFoundException("일치하는 ID의 게시글이 없어요!"));
        post.update(postUpdateReqDto.getTitle(), postUpdateReqDto.getContents());
        postRepository.save(post);
    }

    public void delete(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("일치하는 ID의 게시글이 없어요!"));
        postRepository.delete(post);
    }
}
