package com.encore.board.post.service;

import com.encore.board.post.domain.Post;
import com.encore.board.post.dto.PostDetailResDto;
import com.encore.board.post.dto.PostListResDto;
import com.encore.board.post.dto.PostSaveReqDto;
import com.encore.board.post.dto.PostUpdateReqDto;
import com.encore.board.post.repository.PostRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public void save(PostSaveReqDto postSaveReqDto) {
        Post post = new Post(postSaveReqDto.getTitle(), postSaveReqDto.getContents());
        postRepository.save(post);
    }

    public List<PostListResDto> findAll() {
        List<PostListResDto> postListResDtos = new ArrayList<>();
        for (Post p : postRepository.findAll()) {
            postListResDtos.add(new PostListResDto(p.getId(), p.getTitle()));
        }
        return postListResDtos;
    }

    public PostDetailResDto findById(Long id){
        Post post = postRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("일치하는 ID의 게시글이 없어요!"));
        PostDetailResDto postDetailResDto= new PostDetailResDto(post.getId(), post.getTitle(), post.getContents(), post.getCreatedTime());
        return postDetailResDto;
    }

    public void update(PostUpdateReqDto postUpdateReqDto){
        Post post = postRepository.findById(postUpdateReqDto.getId()).orElseThrow(()->new EntityNotFoundException("일치하는 ID의 게시글이 없어요!"));
        post.update(postUpdateReqDto.getTitle(), postUpdateReqDto.getContents());
        postRepository.save(post);
    }

    public void delete(Long id){
        Post post = postRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("일치하는 ID의 게시글이 없어요!"));
        postRepository.delete(post);
    }
}
