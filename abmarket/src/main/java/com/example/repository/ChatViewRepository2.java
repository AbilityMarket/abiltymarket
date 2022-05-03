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
}
