package com.example.repository;

import java.util.List;

import com.example.entity.ChatViewEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatViewRepository2 extends JpaRepository<ChatViewEntity, Long> {

    // 채팅방 목록 조회하기 시간순으로
    // List<ChatViewEntity>
    // findByClickpersonOrWriterAndStartMessageAndChstateOrderByCrregdateAsc(String
    // user1,
    // String user2,
    // Long startmessage, String chstate);

    @Query(value = "SELECT * FROM (SELECT * FROM CHATVIEW WHERE START_MESSAGE=:startmessage" +
            ") WHERE CLICKPERSON=:uid1 OR WRITER=:uid2 ORDER BY CRREGDATE DESC", nativeQuery = true)
    List<ChatViewEntity> findChatroom(@Param(value = "uid1") String uid1, @Param(value = "uid2") String uid2,

            @Param(value = "startmessage") Long startmessage);
}
