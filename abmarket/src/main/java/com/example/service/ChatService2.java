package com.example.service;

import java.util.List;

import com.example.entity.ChatEntity;
import com.example.entity.ChatroomEntity;

import org.springframework.stereotype.Service;

@Service
public interface ChatService2 {

    // 채팅넣기
    public int insertChat(String token, Long crno);

    // 채팅조회
    public List<ChatEntity> selectChatList(String token, ChatroomEntity chatRoom);

    // 채팅방조회
    public List<ChatroomEntity> selectChatRoomList(String token);

    // 채팅방삭제
    public int deleteChatRoom(String token, Long crno);

    //

}
