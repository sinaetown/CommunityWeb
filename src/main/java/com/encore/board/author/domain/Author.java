package com.encore.board.author.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
//@Builder
//@AllArgsConstructor
//위와 같이 모든 매개변수가 있는 생성자를 새엇ㅇ하는 어노테이션과 Builder를 클래스에 붙여서
//모든 매개변수가 있는 생성자 위에 Builder 어노테이션을 붙인 것과 같은 효과가 있다
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false, length = 20, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @CreationTimestamp
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdTime;

    @UpdateTimestamp
    @Column(columnDefinition = "TIMESTAMP ON UPDATE CURRENT_TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime updatedTime;

    public enum Role {
        ADMIN,
        USER
    }
    @Builder
//    @Buildr를 통해 빌더 패턴으로 객체 생성
//    매개변수의 세팅 순서와 매개변수의 개수 등을 유연하게 세팅할 수 있음
    public Author(String name, String email, String password, Role role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public void update(String name, String password, Role role) {
        this.name = name;
        this.password = password;
        this.role = role;
    }
}
