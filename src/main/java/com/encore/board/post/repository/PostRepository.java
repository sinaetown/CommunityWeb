package com.encore.board.post.repository;

import com.encore.board.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
//    Paging 기술 사용하지 않을 때:
//    List<Post> findAllByOrderByCreatedTimeDesc();

    //    Paging 기술 사용 시:
//    Pageable 객체 안에: pageNumber(page=1), page마다 보일 데이터의 개수(size=10), 정렬(sort=createdTime, desc)에 대한 설정
//    Page : List<Post> + 해당 Page의 각종 정보
    Page<Post> findAll(Pageable pageable);

    Page<Post> findByAppointment(String appointment, Pageable pageable);

    //    select p.* from post p left join author a on p.author_id = a.id;
//    그대로 N+1 문제 발생
//    아래 jpql의 join문은 author객체를 통해 post를 스크리닝하고 싶은 상황에 적합 (ex .. where .. 조건 적용 가능)
    @Query("select p from Post p left join p.author")
    //    jpql문
    List<Post> findAllJoin();

    //    select p.*, a.* from post p left join author a on p.author_id = a.id;
//    N+1 문제 해결!
    @Query("select p from Post p left join fetch p.author")
    //    jpql문
    List<Post> findAllFetchJoin();
}
