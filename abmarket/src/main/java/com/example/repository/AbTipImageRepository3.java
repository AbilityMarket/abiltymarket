package com.example.repository;

import java.util.List;

import com.example.entity.AbTipImageEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AbTipImageRepository3 extends JpaRepository<AbTipImageEntity, Long> {

    // 팁 게시판 조회 시 해당 이미지 가져가기
    // private AbTipEntity abtip;
    List<AbTipImageEntity> findByAbtip_abtno(long abtno);
    
    //List<AbTipImageEntityProjection> findByAbtip_abtno(long abtno);
}
