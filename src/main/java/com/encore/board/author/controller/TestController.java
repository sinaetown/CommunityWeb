package com.encore.board.author.controller;

import com.encore.board.author.service.AuthorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@Slf4j //lombok의 어노테이션을 통해 쉽게 logback 로그 라이브러리 사용 가능
public class TestController {
    //    @Slf4j를 사용하지 않고 직접 라이브러리를 import해서 로거 생성 가능
    private static final Logger log = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private AuthorService authorService;

    @GetMapping("log/test1")
    public String testMethod1() {
        log.debug("this is a debug log");
        log.info("this is a info log");
        log.error("this is a error log");
        return "ok";
    }

    @GetMapping("exception/test1/{id}")
    public String exceptionTestMethod1(@PathVariable(value = "id") Long id) {
        authorService.findById(id);
        return "ok";
    }
}
