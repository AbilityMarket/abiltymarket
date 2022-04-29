package com.example.repository;

import com.example.entity.AlertEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertRepository3 extends JpaRepository<AlertEntity, Long> {
    
    // 읽지 않은 알림 표시 확인(아이콘)
    // alread, alno
    Long countByAlreadAndAlno (Long alread, Long alno);

    // 읽은 알림 1 지우기(아이콘)

}

