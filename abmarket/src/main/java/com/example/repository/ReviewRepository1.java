package com.example.repository;

import java.util.List;

import com.example.entity.ReviewEntity;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository1 extends JpaRepository<ReviewEntity, Long> {

    // 후기 전체 목록 (페이지네이션 + 내용검색)
    List<ReviewEntity> findByRevcontentContainingOrderByRevnoDesc(Pageable page, String revcontent);

    // 후기 총 갯수
    // Long countByBoard_bno(Long bno);
}
