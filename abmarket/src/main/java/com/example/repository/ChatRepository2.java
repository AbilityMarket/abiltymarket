package com.example.repository;

import java.util.List;

import com.example.entity.ChatEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository2 extends JpaRepository<ChatEntity, Long> {

    // 채팅번호로 채팅목록 가져오기
    List<ChatEntity> findByChatroom_crno(Long crno);

    // 읽지 않은 채팅 개수 확인하기
    Long countByReceiveAndUnReadCountAndChatroom_crno(String receive, Long unReadCount, Long crno);

    // 읽었을 때 안읽은 표시 지우기
    // unreadcount 1이고 지금 user가 n이고 crno가 n이고
    List<ChatEntity> findByUnReadCountAndReceiveAndChatroom_crno(Long unReadCount, String receive, Long crno);

    // 채팅개수 구하기
    Long countByChatroom_crno(Long crno);

    // 최근 채팅 찾기
    ChatEntity findTop1ByChatroom_crnoOrderByChregdateDesc(Long crno);
}
