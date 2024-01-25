package com.encore.board.author.repository;

import com.encore.board.author.domain.Author;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
//replace=AutoConfigureTestDatabase.Replace.ANY : H2 DB(spring 내장 인메모리)가 기본설정
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
//@DataJpaTest 어노테이션을 사용하면 매 테스트가 완료되면 자동으로 DB 원상복구
//모든 스프링 빈을 생성하지 않고, DB테스트 특화 어노테이션!

//@ActiveProfiles("test") => application-test.yml 파일을 찾아 설정값 세팅
//@ActiveProfiles("???") => application-???.yml 파일을 찾아 설정값 세팅
//@SpringBootTest
//@Transactional
//@SpringBootTest 어노테이션은 자동 롤백 기능은 지원하지 않고, 별도로 롤백 코드 또는 어노테이션 필요
//실제 스프링 실행고 동일하게 스프링 빈 생성 및 주입
public class AuthorRepositoryTest {
    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void authorSaveTest() {
//        객체를 만들어서 save -> 재조회 (findByEmail) -> 만든 객체와 비교
//        준비 (prepare, given) 단계
        Author author = Author.builder()
                .name("Matt")
                .email("matt@naver.com")
                .password("123123")
                .role(Author.Role.ADMIN)
                .build();
//        실행 (execute, when) 단계
        authorRepository.save(author);
//        검증 (then) 단계
        Author authorDb = authorRepository.findByEmail("matt@naver.com").orElse(null);
//        Assertion 클래스의 기능을 통해 오류의 원인 파악, null 처리, 가시적으로 성공/실패 여부 확인
        Assertions.assertEquals(author.getEmail(), authorDb.getEmail());

    }
}
