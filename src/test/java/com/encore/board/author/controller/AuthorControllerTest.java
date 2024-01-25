//package com.encore.board.author.controller;
//
//import com.encore.board.author.dto.AuthorDetailResDto;
//import com.encore.board.author.service.AuthorService;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
////@WebMvcTest를 이용해서 Controller 계층을 테스트
////모든 스프링 빈을 생성하고 주입하지는 않음
//@WebMvcTest(AuthorController.class) //어떤 클래스를 테스트 할 지 명시
//@SpringBootTest
//@AutoConfigureMockMvc
//public class AuthorControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//    @MockBean
//    private AuthorService authorService;
//
//    @Test
//    @WithMockUser
//        //security 의존성 추가 필요
//    void authorDetailTest() {
//        AuthorDetailResDto authorDetailResDto = AuthorDetailResDto.builder()
//                .name("testname")
//                .email("test@naver.com")
//                .password("1234")
//                .build();
//
//        Mockito.when(authorService.findAuthorDetail(1L)).thenReturn(authorDetailResDto);
//        mockMvc.perform(MockMvcRequestBuilders.get("/author/4/circle/dto"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(jsonPath("$.name", authorDetailResDto.getName()));
//    }
//
//}
