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
            if (board != null) {
                MemberEntity member = board.getMember();
                String boardWriterid = member.getUid();
                ChatroomEntity chatroom = chatroomRepository2.findByMember_uidAndBoard_member_uid(uid, boardWriterid);
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
            ChatroomEntity chatroom = new ChatroomEntity();
            chatroomRepository2.save();
        } catch (Exception e) {
            // TODO: handle exception
        }
        return 0;
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

        return null;
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
