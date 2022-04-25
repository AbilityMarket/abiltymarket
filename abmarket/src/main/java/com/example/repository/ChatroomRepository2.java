package com.example.repository;

import com.example.entity.ChatroomEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatroomRepository2 extends JpaRepository<ChatroomEntity, Long> {

    // 게시판 안에 아이디, 유저 아이디, 대화여부
    ChatroomEntity findByMember_uidAndBoard_member_uid(String userId, String boardWriterid);

    // findByLastnameAndFirstname
    // 이게 안되면 프로젝션을 만들자

}
