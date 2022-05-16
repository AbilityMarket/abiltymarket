package com.example.repository;

import com.example.entity.AlertEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertRepository3 extends JpaRepository<AlertEntity, Long> {
    
    // 읽지 않은 알림 표시 확인(아이콘)
    // alread, alno
    Long countByAlreadAndAlno (Long alread, Long alno);

    // UPDATE READ 0 1 구분하기

    // 알림 DB 중 alread 상태 확인
    // alno, uid, altype, alurl
    AlertEntity findByAlreadAndMember_uidAndAltypeAndAlurlAndAlno(Long alread, String uid, Long altype, String alurl, Long alno);

}

