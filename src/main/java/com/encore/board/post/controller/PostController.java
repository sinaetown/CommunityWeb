package com.encore.board.post.controller;

import com.encore.board.post.dto.PostSaveReqDto;
import com.encore.board.post.dto.PostUpdateReqDto;
import com.encore.board.post.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/post/create")
    public String postCreate(){
        return "/post/post-create";
    }

    @PostMapping("/post/create")
    public String postSave(PostSaveReqDto postSaveReqDto) {
        postService.save(postSaveReqDto);
        return "redirect:/post/list";
    }

    @GetMapping("/post/list")
    public String postList(Model model) {
        model.addAttribute("postList", postService.findAll());
        return "/post/post-list";
    }

    @GetMapping("/post/detail/{id}")
    public String postDetail(@PathVariable(value = "id") Long id, Model model) {
        model.addAttribute("post", postService.findById(id));
        return "/post/post-detail";
    }

    @PostMapping("/post/{id}/update")
    public String postUpdate(@PathVariable(value = "id") Long id, PostUpdateReqDto postUpdateReqDto) {
        postService.update(postUpdateReqDto);
        return "redirect:/post/detail/"+id;
    }

    @GetMapping("/post/delete/{id}")
    public String postDelete(@PathVariable(value = "id") Long id) {
        postService.delete(id);
        return "redirect:/post/list";
    }

}
