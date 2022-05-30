package com.example.repository;

import java.util.List;

import com.example.entity.AlertEntity;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertRepository3 extends JpaRepository<AlertEntity, Long> {
    
    // 읽지 않은(1) 알림 갯수 호출
    // alread, alno
    Long countByAlreadAndMember_uid(Long alread, String uid);

    // 알림 DB 중 alread 상태 확인
    // alno, uid, altype, alurl
    AlertEntity findByAlreadAndMember_uidAndAltypeAndAlurlAndAlno(Long alread, String uid, Long altype, String alurl, Long alno);

    // 알림 전체 목록 조회 불러오기(페이지) (검색X)
    // 읽기 여부 상관X
    List<AlertEntity> findByMember_uid(Pageable page, String uid);

    // 읽지 않은 알림 목록 조회
    List<AlertEntity> findByMember_uidAndAlread(String uid, Long alread);
    
}

