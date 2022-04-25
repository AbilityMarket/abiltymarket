package com.example.repository;

import java.util.List;

import com.example.entity.ChatroomEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatroomRepository2 extends JpaRepository<ChatroomEntity, Long> {

    // 게시판 안에 아이디, 유저 아이디, 대화여부
    ChatroomEntity findByMember_uidAndBoard_member_uid(String userId, String boardWriterid);

    // 토큰에 있는 아이디로 채팅방 찾기
    List<ChatroomEntity> findByMember_uid(String uid);

}
