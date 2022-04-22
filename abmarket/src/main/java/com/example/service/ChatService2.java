package com.example.service;

import java.util.List;

import com.example.entity.ChatEntity;
import com.example.entity.ChatImageEntity;
import com.example.entity.ChatroomEntity;

import org.springframework.stereotype.Service;

@Service
public interface ChatService2 {

    // DB에 채팅방이 있는지 확인
    public int searchChatRoom(String token, Long bno);

    // 채팅방저장() (토큰, 게시글 번호)
    public int createChat(String token, Long bno);

    // 채팅 메시지 내용 조회(채팅방 선택해서 들어감)
    public List<ChatEntity> selectChatList(String token, Long crno);

    // 채팅 메시지 넣기(채팅입력)(토큰, 채팅방 번호)
    public int insertMessage(String token, Long crno);

    // 채팅방조회(채팅방리스트)
    public List<ChatroomEntity> selectChatRoomList(String token);

    // 채팅방삭제 (토큰, 채팅방 번호)
    public int deleteChatRoom(String token, Long crno);

    // 이미지 추가 (토큰, 이미지, 채팅방 번호)
    public int insertImage(String token, ChatImageEntity chatimage, Long crno);

    // 이미지 보여주기 (토큰, 이미지 번호)
    public ChatImageEntity selectItemImageOne(String token, Long chno);

    // 읽지 않은 메시지 수 출력 ()
    public int selectUnReadCount(String token, ChatEntity chat);

    // 읽은 메세지 숫자 0으로 바꾸기
    public int updateCount(String token, ChatEntity chat);

}
