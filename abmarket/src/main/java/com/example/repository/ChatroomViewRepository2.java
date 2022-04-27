package com.example.repository;

import java.util.List;

import com.example.entity.ChatroomViewEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatroomViewRepository2 extends JpaRepository<ChatroomViewEntity, Long> {

    // 채팅방 목록 조회하기 시간순으로
    List<ChatroomViewEntity> findByStartMessageAndWriterOrClickpersonOrderByCrregdateAsc(Long startmessage,
            String user1, String user2);
}
