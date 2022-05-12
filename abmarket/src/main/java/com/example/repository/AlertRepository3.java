package com.example.repository;

import com.example.entity.AlertEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertRepository3 extends JpaRepository<AlertEntity, Long> {
    
    // 읽지 않은 알림 표시 확인(아이콘)
    // alread, alno
    Long countByAlreadAndAlno (Long alread, Long alno);

    // 회원 본인의 알림 목록 확인
    // almessage, uid = cc, alreaddate(확인일자), alregdate(생성일자)
    // SELECT ALMESSAGE FROM ALERT WHERE UID = cc AND ALREADDATE IS NULL ORDER BY ALREGDATE LIMIT 50 OFFSET 0
    // @Query(value = "")

    // UPDATE READ 0 1 구분하기
}

