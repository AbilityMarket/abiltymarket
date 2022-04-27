package com.example.service;

import java.util.List;

import com.example.entity.BoardEntity;
import com.example.entity.ChatEntity;
import com.example.entity.ChatImageEntity;
import com.example.entity.ChatroomEntity;
import com.example.entity.ChatroomViewEntity;
import com.example.entity.MemberEntity;
import com.example.repository.BoardRepository2;
import com.example.repository.ChatRepository2;
import com.example.repository.ChatroomRepository2;
import com.example.repository.ChatroomViewRepository2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl2 implements ChatService2 {

    @Autowired
    ChatRepository2 cRepository2;

    @Autowired
    ChatroomRepository2 chatroomRepository2;

    @Autowired
    ChatroomViewRepository2 chatroomViewRepository2;

    @Autowired
    BoardRepository2 bRepository2;

    // 채팅방이 있나 확인하기
    @Override
    public ChatroomEntity searchChatRoom(String uid, Long bno) {
        try {
            BoardEntity board = bRepository2.findById(bno).orElse(null);
            // System.out.println(board);
            if (board != null) {
                MemberEntity member = board.getMember();
                String boardWriterid = member.getUid();
                ChatroomEntity chatroom = chatroomRepository2.findByMember_uidAndBoard_member_uid(uid, boardWriterid);
                System.out.println(uid);
                System.out.println(boardWriterid);
                return chatroom;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 채팅방 만들기
    @Override
    public int createChatRoom(String uid, Long bno) {
        try {
            // 멤버
            MemberEntity member = new MemberEntity();
            member.setUid(uid);

            BoardEntity board = bRepository2.findById(bno).orElse(null);
            if (board != null) {
                System.out.println("토큰에서가져온멤버" + uid);
                System.out.println("게시판에거사져온멤버" + board.getMember().getUid());
                // 본인글일 경우
                if (board.getMember().getUid().equals(uid)) {
                    System.out.println("본인글");
                    return 3;
                }
                // 본인 글 아닐경우
                else {
                    System.out.println("여기2");
                    ChatroomEntity chatroom = new ChatroomEntity();
                    chatroom.setBoard(board);
                    chatroom.setMember(member);
                    ChatroomEntity retChatroom = chatroomRepository2.save(chatroom);
                    // 저장이 정상적으로 된다면
                    if (retChatroom != null) {
                        System.out.println("정상작동 저장");
                        return 1;
                    }
                    // 저장이 정상적으로 안됐다면
                    else {
                        System.out.println("정상작동 저장실패");
                        return 2;
                    }
                }

                // 보드가 없는 경우
            } else {
                System.out.println("보드가 없으");
                return 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }

    }

    // 채팅목록
    @Override
    public List<ChatEntity> selectChatList(Long crno) {
        try {
            // 채팅번호로 채팅목록 가져오기
            return cRepository2.findByChatroom_crno(crno);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    // 채팅입력
    @Override
    public int insertMessage(ChatEntity chat) {

        try {
            cRepository2.save(chat);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }

    // 채팅방 목록 가져오기 (내가 보낸 거 + 받은 거)
    @Override
    public List<ChatroomViewEntity> selectChatRoomList(String uid) {
        try {

            List<ChatroomViewEntity> list = chatroomViewRepository2
                    .findByStartMessageAndWriterOrClickpersonOrderByCrregdateAsc(1L, uid,
                            uid);
            // System.out.println(list);
            return list;
            // list에서 START_MESSAGE가 1인거
            // chatroomRepository2.findByS

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int deleteChatRoom(String uid, Long crno) {

        return 0;
    }

    @Override
    public int insertImage(String uid, ChatImageEntity chatimage, Long crno) {

        return 0;
    }

    @Override
    public ChatImageEntity selectItemImageOne(String uid, Long chno) {

        return null;
    }

    // 안읽은 채팅 표시하기
    @Override
    public Long selectUnReadCount(String uid, Long crno) {
        try {
            Long count = 1L;
            Long unReadCount = cRepository2.countByReceiveAndUnReadCountAndChatroom_crno(uid, count, crno);
            return unReadCount;
        }

        catch (Exception e) {
            e.getStackTrace();
            return null;
        }

    }

    // 채팅 읽었을 때 안읽은 표시 지우기
    @Override
    public int updateCount(Long crno, String uid) {
        try {
            // cRepository2.updateCount(crno, uid);
            return 1;
        }

        catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int updateStartMessage(ChatroomEntity chatroom) {
        try {
            chatroomRepository2.save(chatroom);
            return 1;
        } catch (Exception e) {
            e.getStackTrace();
            return 0;
        }

    }

}
