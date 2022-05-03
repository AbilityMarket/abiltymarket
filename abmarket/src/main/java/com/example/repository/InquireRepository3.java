package com.example.repository;

import java.util.List;

import com.example.entity.InquireEntity;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InquireRepository3 extends JpaRepository<InquireEntity, Long> {
    
    // 문의 전체 목록 조회 시 검색과 페이지네이션
    // Inqtitle, Inqno
    List<InquireEntity> findByInqtitleContainingOrderByInqnoDesc(Pageable page, String text);

    // 검색 결과 전체 갯수
    // Inqtitle
    Long countByInqtitleContaining(String text);

}
