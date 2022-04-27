package com.example.repository;

import java.util.List;

import com.example.entity.ChatEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository2 extends JpaRepository<ChatEntity, Long> {

    // 채팅번호로 채팅목록 가져오기
    List<ChatEntity> findByChatroom_crno(Long crno);

    // 안 읽은 채팅 개수 확인하기
    @Query(value = "SELECT SUM(UN_READ_COUNT) FROM CHAT WHERE UN_READ_COUNT=:unReadCount AND CHATROOM_CRNO=:crno", nativeQuery = true)
    Long countUnReadCount(Long unReadCount, Long crno);

    // // @Query(value = "UPDATE CHAT SET UN_READ_COUNT=0L WHERE CHATROOM_CRNO=:crno
    // AND CHATROOM_", nativeQuery = true)
    // int updateCount(Long crno, String uid);
}
