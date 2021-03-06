package com.example.service;

import java.util.List;
import java.util.Map;

import com.example.entity.ChatEntity;
import com.example.entity.ChatroomEntity;
import com.example.entity.ChatViewEntity;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface ChatService2 {

    // DB에 채팅방이 있는지 확인
    public int searchChatRoom(String uid, Long bno);

    // 채팅방저장() (토큰, 게시글 번호)
    public int createChatRoom(String uid, Long bno);

    // 채팅방저장() (토큰, 게시글 번호)
    public Map<String, Object> createChatRoom2(String uid, Long bno);

    // 채팅 메시지 내용 조회(채팅방 선택해서 들어감)
    public List<ChatEntity> selectChatList(Pageable pageable, Long crno);

    // 채팅 메시지 넣기(채팅입력)(채팅)
    public int insertMessage(ChatEntity chat);

    // 채팅방조회(채팅방리스트)
    public List<ChatViewEntity> selectChatRoomList(String uid);

    // 채팅방삭제 (토큰, 채팅방 번호)
    public int deleteChatRoom(String uid, Long crno);

    // 읽지 않은 메시지 수 출력 ()
    public List<Long> selectUnReadCount(String uid, List<Long> crno);

    // 읽은 메세지 숫자 0으로 바꾸기
    public int updateCount(Long crno, String uid);

    // 대화 시작한 채팅방 startmessage 상태바꾸기
    public int updateStartMessage(ChatroomEntity chatroom);

    // 상대가 나갔음을 표시하기
    public int noteExit(String uid, Long crno);

    // 채팅 나눈 개수 표시하기(게시판에 하트, 채팅 수 확인용)
    public Long chatcount(Long bno);

    // 거래예약하기
    public int makeReservation(Long crno);

    // 거래 완료
    public int doneTrade(Long crno);

}
