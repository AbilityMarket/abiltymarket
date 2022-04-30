package com.example.repository;

import java.util.List;
import java.util.Map;

import com.example.entity.AbTipEntity;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AbTipRepository3 extends JpaRepository<AbTipEntity, Long> {

    // 팁 전체 목록 조회 시 검색(Abttitle) + 페이지네이션
    // Abttitle, Abtno
    List<AbTipEntity> findByAbttitleContainingOrderByAbtnoDesc(Pageable page, String abttitle);

    // 검색어가 포함 된 전체 갯수
    // Abttitle
    Long countByAbttitleContaining(Map<String, Object> map);


    //List<AbTipImageEntity> deleteByAbino(Long abino);

}
