package com.example.repository;

import java.util.List;

import com.example.entity.ChatroomEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatroomRepository2 extends JpaRepository<ChatroomEntity, Long> {

    // 게시판 안에 아이디, 유저 아이디, 대화여부
    ChatroomEntity findByMember_uidAndBoard_member_uid(String userId, String boardWriterid);

    // 토큰에 있는 아이디, 내가 올린 글에 누가 대화하기를 클릭해서 만들어진 채팅방 찾기
    List<ChatroomEntity> findByMember_uidOrBoard_member_uid(String uid, String uid2);

    // 만들어진 채팅방에서도 대화가 시작한 채팅방 리스트
    // @Query(value = "SELECT * FROM (SELECT * FROM(SELECT B.MEMBER_UID WRITER, " +
    // "C.CRNO,C.CRREGDATE,C.CRREPORT,C.BOARD_BNO, C.START_MESSAGE, " +
    // "C.MEMBER_UID CLICKPERSON FROM BOARD B, CHATROOM C WHERE " +
    // "B.BNO=C.BOARD_BNO)WHERE WRITER=:uid1 OR CLICKPERSON=:uid2)" +
    // "WHERE START_MESSAGE=1", nativeQuery = true)
    // List<ChatroomProjection> findMessage(@Param(value = "uid1") String uid1,
    // @Param(value = "uid2") String uid2);

    // @Query(value = "SELECT SUM(UN_READ_COUNT) FROM CHAT WHERE
    // UN_READ_COUNT=:unReadCount AND CHATROOM_CRNO=:crno", nativeQuery = true)
}
