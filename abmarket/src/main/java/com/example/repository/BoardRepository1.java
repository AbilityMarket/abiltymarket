package com.example.repository;

import java.util.List;

import com.example.entity.BoardEntity;
import com.example.entity.BoardProjection;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository1 extends JpaRepository<BoardEntity, Long> {

    List<BoardEntity> findByBtitleContainingOrderByBnoDesc(Pageable page, String btitle);

    // 만약 게시판 글을 쓸 때 바로 관심사 설정을 할 수 있다면
    // 게시판 엔티티에 관심사 엔티티랑 연결
    // in 이용해서 작성하기 INTEREST_incode 또는 inname, incategory 이용
    // List<BoardEntity> findByInterest_incode(List<Log> incode);

    BoardProjection findByBno(Long bno);

    List<BoardEntity> findByMember_uid(String uid);
}
