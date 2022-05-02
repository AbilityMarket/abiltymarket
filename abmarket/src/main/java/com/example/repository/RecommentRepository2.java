package com.example.repository;

import com.example.entity.RecommentEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecommentRepository2 extends JpaRepository<RecommentEntity, Long> {

    // 대댓글 개수구하기
    Long countByComm_cono(Long cono);
}
