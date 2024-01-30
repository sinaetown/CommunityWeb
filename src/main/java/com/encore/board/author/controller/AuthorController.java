package com.encore.board.author.controller;

import com.encore.board.author.domain.Author;
import com.encore.board.author.dto.AuthorDetailResDto;
import com.encore.board.author.dto.AuthorSaveReqDto;
import com.encore.board.author.dto.AuthorUpdateReqDto;
import com.encore.board.author.service.AuthorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@Slf4j
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/author/create")
    public String authorCreate() {
        return "/author/author-create";
    }

    @GetMapping("/author/login-page")
    public String authorLogin() {
        return "/author/login-page";
    }

    @PostMapping("/author/create")
    public String authorSave(AuthorSaveReqDto authorSaveReqDto, Model model) {
        try {
            authorService.save(authorSaveReqDto);
            return "redirect:/author/list";

        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            log.error(e.getMessage());
            return "/author/author-create";
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/author/list")
    public String authorList(Model model) {
        model.addAttribute("authorList", authorService.findAll());
        return "/author/author-list";
    }

    /*---------------------------------SO -- Testing of Vue & authorList--------------------------------- */
//    @CrossOrigin(origins = "*") //for connecting Vue.js -> testing!
//    @GetMapping("/author/list")
//    @ResponseBody
//    public List<AuthorListResDto> authorList(Model model) {
////        model.addAttribute("authorList", authorService.findAll());
//        return  authorService.findAll();
//    }
    /*---------------------------------EO -- Testing of Vue & authorList--------------------------------- */

    @GetMapping("/author/detail/{id}")
    public String authorDetail(@PathVariable Long id, Model model) {
        model.addAttribute("author", authorService.findById(id));
        return "/author/author-detail";
    }

    @PostMapping("/author/{id}/update")
    public String authorUpdate(@PathVariable(value = "id") Long id, AuthorUpdateReqDto authorUpdateReqDto) {
        authorService.update(authorUpdateReqDto);
        return "redirect:/author/detail/" + id;
    }

    @GetMapping("/author/delete/{id}")
    public String authorDelete(@PathVariable(value = "id") Long id) {
        authorService.delete(id);
        return "redirect:/author/list";
    }

    //    엔티티 순환참조 이슈 테스트
//    연관관계가 있는 Author엔티티를 json으로 직렬화를 하게될 경우,
//    순환참조 이슈가 발생하므로 DTO가 필요하다
    @GetMapping("/author/{id}/circle/entity")
    @ResponseBody
    public Author circleEntity(@PathVariable(value = "id") Long id) {
        return authorService.findById(id);
    }

    @GetMapping("/author/{id}/circle/dto")
    @ResponseBody
    public AuthorDetailResDto circleDto(@PathVariable(value = "id") Long id) {
        return authorService.findAuthorDetail(id);
    }

}
