package com.encore.board.post.controller;

import com.encore.board.post.dto.PostListResDto;
import com.encore.board.post.dto.PostSaveReqDto;
import com.encore.board.post.dto.PostUpdateReqDto;
import com.encore.board.post.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/post/create")
    public String postCreate() {
        return "/post/post-create";
    }

    @PostMapping("/post/create")
    public String postSave(PostSaveReqDto postSaveReqDto, Model model) {
        try {
            postService.save(postSaveReqDto);
            return "redirect:/post/list";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "/post/post-create";
        }

    }

    @GetMapping("/post/list")
    public String postList(Model model, @PageableDefault(size = 3, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<PostListResDto> postListResDtos = postService.findByAppointment(pageable);
        model.addAttribute("postList", postListResDtos);
        return "post/post-list";

    }

    @GetMapping("/json/post/list")
//    localhost:8080/json/post/list?size=xx&page=xx&sort=xx,desc
    @ResponseBody
    public Page<PostListResDto> postListJson(Pageable pageable) {
        Page<PostListResDto> postListResDtos = postService.findAllPaging(pageable);
        return postListResDtos;
    }

    @GetMapping("/post/detail/{id}")
    public String postDetail(@PathVariable(value = "id") Long id, Model model) {
        model.addAttribute("post", postService.findById(id));
        return "/post/post-detail";
    }

    @PostMapping("/post/{id}/update")
    public String postUpdate(@PathVariable(value = "id") Long id, PostUpdateReqDto postUpdateReqDto) {
        postService.update(postUpdateReqDto);
        return "redirect:/post/detail/" + id;
    }

    @GetMapping("/post/delete/{id}")
    public String postDelete(@PathVariable(value = "id") Long id) {
        postService.delete(id);
        return "redirect:/post/list";
    }

}
