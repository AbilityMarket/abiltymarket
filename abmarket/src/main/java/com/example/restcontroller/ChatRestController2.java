package com.example.restcontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.entity.ChatEntity;
import com.example.entity.ChatroomEntity;
import com.example.jwt.JwtUtil;
import com.example.repository.ChatroomRepository2;
import com.example.service.ChatService2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/chat")
public class ChatRestController2 {

    // 토큰 가져오기
    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    ChatService2 cService2;

    @Autowired
    ChatroomRepository2 chatroomRepository2;

    // 채팅입력
    @RequestMapping(value = "/insert", method = { RequestMethod.POST }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> insertChat(
            @RequestHeader String token) {

        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        // 토큰에서 이메일 추출
        String username = jwtUtil.extractUsername(token);
        System.out.println(username);

        try {

            map.put("status", 200);
        } catch (Exception e) {
            map.put("status", -1);
            e.printStackTrace();
        }
        return map;
    }

    // 채팅방있나 확인하고 없으면 만들기
    @RequestMapping(value = "/checkRoom", method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> checkRoom(
            @RequestHeader String token,
            @RequestParam(name = "bno") Long bno) {
        Map<String, Object> map = new HashMap<>();
        try {
            // 토큰에서 현재 사용자 아이디 뽑아내기
            String uid = jwtUtil.extractUsername(token);
            // System.out.println(uid);

            // 채팅방 유무 확인하기
            ChatroomEntity chatroom = cService2.searchChatRoom(uid, bno);

            // 채팅방이 있으면 0리턴
            if (chatroom != null) {
                map.put("status", 0);
                map.put("result", "채팅방 있음");
            }
            // 채팅방이 없으면 채팅방 만들기
            else {
                int ret = cService2.createChatRoom(uid, bno);
                System.out.println(ret);
                // 저장이 제대로 되는 경우
                if (ret == 1) {
                    map.put("status", 200);
                    map.put("result", "채팅방생성");
                }
                // 본인 글일 경우
                else if (ret == 3) {
                    map.put("status", 3);
                    map.put("result", "본인 글임");
                }
                // userid나 bno가 없는게 전달될 경우
                else {
                    map.put("status", 2);
                    map.put("result", "userid나 bno없음");
                }
            }
        } catch (Exception e) {
            map.put("status", -1);
            e.printStackTrace();
        }
        return map;
    }

    // 채팅방 리스트 불러오기
    @RequestMapping(value = "/selectlist", method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> selectlistChatRoom(
            @RequestHeader String token) {

        Map<String, Object> map = new HashMap<>();
        try {
            // 토큰에서 현재 사용자 아이디 뽑아내기
            String uid = jwtUtil.extractUsername(token);

            // 채팅번호로 채팅목록 가져오거나,
            List<ChatroomEntity> list = cService2.selectChatRoomList(uid);
            if (list != null) {
                map.put("status", 200);
                map.put("result", list);
            } else {
                map.put("status", 0);
            }

        } catch (Exception e) {
            map.put("status", -1);
            e.printStackTrace();
        }
        return map;
    }

    // 채팅 입력하기
    @RequestMapping(value = "/sendMessage", method = { RequestMethod.POST }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> sendMessage(
            @RequestHeader(name = "token") String token,
            @ModelAttribute ChatEntity chat,
            @RequestParam(name = "crno") Long crno) {

        Map<String, Object> map = new HashMap<>();
        try {
            // 채팅방 번호담기
            ChatroomEntity chatroom = new ChatroomEntity();
            chatroom.setCrno(crno);

            // 토큰에서 현재 사용자 아이디 뽑아내기
            String uid = jwtUtil.extractUsername(token);

            ChatroomEntity chatroom2 = chatroomRepository2.findById(crno).orElse(null);
            // System.out.println(chatroom2);
            // 지금 로그인한 사람이 채팅 시작한 사람일 경우
            if (uid == chatroom2.getMember().getUid()) {
                chat.setSend(uid);
                chat.setReceive(chatroom2.getBoard().getMember().getUid());
            } else {
                chat.setSend(chatroom2.getBoard().getMember().getUid());
                chat.setReceive(uid);
            }
            // 채팅 담기
            chat.setChatroom(chatroom);
            chat.setReview(null);
            // chat.setSend(send);
            // chat.setReceive(receive);
            // 넣기
            int ret = cService2.insertMessage(chat);
            if (ret == 1) {
                map.put("status", 200);
                map.put("result", "잘들어감");
            } else {
                map.put("status", 0);
                map.put("result", "잘안들어감");
            }

        } catch (Exception e) {
            map.put("status", -1);
            e.printStackTrace();
        }
        return map;
    }

    // 채팅 목록 불러오기
    @RequestMapping(value = "/messageList", method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> messageList(
            @RequestParam(name = "crno") Long crno) {

        Map<String, Object> map = new HashMap<>();
        try {
            List<ChatEntity> list = cService2.selectChatList(crno);

            if (list.size() > 0) {
                map.put("result", list);
                map.put("status", 200);
            } else {

                map.put("status", 0);
            }

        } catch (Exception e) {
            map.put("status", -1);
            e.printStackTrace();
        }
        return map;
    }

    // 안읽은 채팅 표시하기
    @RequestMapping(value = "/unReadCount", method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> unReadCount(
            @RequestParam(name = "crno") Long crno) {

        Map<String, Object> map = new HashMap<>();
        try {
            Long unReadCount = cService2.selectUnReadCount(crno);
            if (unReadCount != null) {
                map.put("count", unReadCount);
                map.put("status", 200);
            } else {

                map.put("result", "채팅방없음");
                map.put("status", 0);
            }
        }

        catch (Exception e) {
            map.put("status", -1);
            e.printStackTrace();
        }
        return map;
    }

    // 채팅 읽었을 때 안읽은 표시 지우기
    @RequestMapping(value = "/updateCount", method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> updateCount(
            @RequestHeader(name = "token") String token,
            @RequestParam(name = "crno") Long crno) {
        Map<String, Object> map = new HashMap<>();
        try {
            String uid = jwtUtil.extractUsername(token);
            int ret = cService2.updateCount(crno, uid);
            if (ret == 1) {
                map.put("status", 200);
            } else {
                map.put("status", 0);
            }

        } catch (Exception e) {
            map.put("status", -1);
            e.printStackTrace();
        }
        return map;
    }
}
