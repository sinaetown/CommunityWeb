package com.encore.board.author.domain;

import com.encore.board.post.domain.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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

//    author 조회 시, Post객체가 필요할 시에 선언
//    mappedBy에 연관관계의 주인(FK를 관리하는)의 변수명을 명시 <-> '부모' 객체와는 개념적 구별 필요
//    일반적으로 부모 객체에서 Cascade 옵션을 걺
//    Cascade와 dirty check는 다름 : 변경사항을 체크하는 게 dirty check
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @Setter //Cascade.PERSIST를 위한 테스트
    private List<Post> posts;

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
    public Author(String name, String email, String password, Role role, List<Post> posts) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.posts = posts;
    }

    public void update(String name, String password, Role role) {
        this.name = name;
        this.password = password;
        this.role = role;
    }
}
