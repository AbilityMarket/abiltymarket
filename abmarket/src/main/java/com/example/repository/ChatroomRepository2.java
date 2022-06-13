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
    
    List<ChatroomEntity> findByMember_uid(String uid);

}
