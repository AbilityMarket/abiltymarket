package com.example.service;

import java.util.List;

import com.example.entity.ChatEntity;
import com.example.entity.ChatImageEntity;
import com.example.entity.ChatroomEntity;
import com.example.repository.ChatRepository2;
import com.example.repository.ChatroomRepository2;

import org.springframework.beans.factory.annotation.Autowired;

public class ChatServiceImpl2 implements ChatService2 {

    @Autowired
    ChatRepository2 cRepository2;

    @Autowired
    ChatroomRepository2 chatroomRepository2;

    @Override
    public int searchChatRoom(String uid, Long bno) {

        return 0;
    }

    @Override
    public int createChat(String uid, Long bno) {

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
