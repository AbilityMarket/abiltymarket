package com.example.restcontroller;

import java.util.HashMap;
import java.util.Map;

import com.example.entity.ChatroomEntity;
import com.example.jwt.JwtUtil;
import com.example.service.ChatService2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
    public Map<String, Object> insertBoard3(
            @RequestHeader String token,
            @RequestParam(name = "bno") Long bno) {
        // 여기서 뽑아내야 하는 게 토큰에 들어있는 아이디랑, 보드 안에 들어있는 아이디
        Map<String, Object> map = new HashMap<>();
        try {
            // 토큰에서 현재 사용자 아이디 뽑아내기
            String userid = jwtUtil.extractUsername(token);
            System.out.println(userid);

            ChatroomEntity chatroom = cService2.searchChatRoom(userid, bno);
            // 채팅방이 있으면 0리턴
            if (chatroom != null) {
                map.put("status", 0);
            }
            // 채팅방이 없으면 채팅방 만들기
            else {
                cService2.createChatRoom(userid, bno);
                map.put("status", 200);

            }

        } catch (Exception e) {
            map.put("status", -1);
            e.printStackTrace();
        }
        return map;
    }
}
