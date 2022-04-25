package com.example.service;

import java.util.List;

import com.example.entity.BoardEntity;
import com.example.entity.ChatEntity;
import com.example.entity.ChatImageEntity;
import com.example.entity.ChatroomEntity;
import com.example.entity.MemberEntity;
import com.example.repository.BoardRepository2;
import com.example.repository.ChatRepository2;
import com.example.repository.ChatroomRepository2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl2 implements ChatService2 {

    @Autowired
    ChatRepository2 cRepository2;

    @Autowired
    ChatroomRepository2 chatroomRepository2;

    @Autowired
    BoardRepository2 bRepository2;

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

    @Override
    public List<ChatEntity> selectChatList(String uid, Long crno) {

        return null;
    }

    @Override
    public int insertMessage(String uid, Long crno) {

        return 0;
    }

    @Override
    public List<ChatroomEntity> selectChatRoomList(String uid) {
        try {
            return chatroomRepository2.findByMember_uid(uid);
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

    @Override
    public int selectUnReadCount(String uid, ChatEntity chat) {

        return 0;
    }

    @Override
    public int updateCount(String token, ChatEntity chat) {

        return 0;
    }

}
