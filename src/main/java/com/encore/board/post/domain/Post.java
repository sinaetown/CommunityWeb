package com.encore.board.post.domain;

import com.encore.board.author.domain.Author;
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
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50, nullable = false)
    private String title;

    @Column(length = 3000, nullable = false)
    private String contents;

//    post 객체 입장에서는 한 사람이 여러 글을 쓸 수 있음
    @ManyToOne(fetch = FetchType.LAZY)
//    author_id는 DB의 컬럼명, 별다른 옵션이 없을 시, author의 PK에 FK가 설정됨
    @JoinColumn(name="author_id")
//    @JoinColumn(nullable=false, name="author_email", referencedColumnName = "email") -> author의 email을 FK로 걸어
    private Author author;

    @CreationTimestamp
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdTime;

    @UpdateTimestamp
    @Column(columnDefinition = "TIMESTAMP ON UPDATE CURRENT_TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime updatedTime;

    @Builder
    public Post(String title, String contents, Author author){
        this.title= title;
        this.contents= contents;
        this.author = author;
//        author객체의 posts를 초기화한 뒤,
//        this.author.getPosts().add(this);
    }

    public void update(String title, String contents){
        this.title = title;
        this.contents = contents;
    }
}
