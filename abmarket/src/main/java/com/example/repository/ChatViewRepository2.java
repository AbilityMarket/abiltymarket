package com.example.repository;

import java.util.List;

import com.example.entity.ChatViewEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatViewRepository2 extends JpaRepository<ChatViewEntity, Long> {

        @Query(value = "SELECT * FROM (SELECT * FROM CHATVIEW WHERE START_MESSAGE=:startmessage" +
                        ") WHERE CLICKPERSON=:uid1 OR WRITER=:uid2 ORDER BY CRREGDATE DESC", nativeQuery = true)
        List<ChatViewEntity> findChatroom(@Param(value = "uid1") String uid1, @Param(value = "uid2") String uid2,

                        @Param(value = "startmessage") Long startmessage);

        // 게시판번호로 챗뷰 리스트찾기
        List<ChatViewEntity> findByBoardBno(Long bno);

        // 게시글쓴이와 로그인 아이디 최근 대화 채팅상태 확인용
        List<ChatViewEntity> findByClickpersonAndWriter(String clickperson, String writer);

        // 게시글쓴이와 로그인 아이디 최근 대화 채팅상태 확인용
        List<ChatViewEntity> findByClickpersonOrWriter(String clickperson, String writer);

        // 챗뷰는 채팅방 개설 후 메세지를 보내야 해당 챗뷰 생성 됨
        // 채팅방 1개당 거래 완료 상태 확인하기
        // crno 는 고유 하니까 리스트 필요없음
        ChatViewEntity findByCrno(Long crno);
        // chstate
        ChatViewEntity findByChstateAndCrno(String chstate, Long crno);
        
}
