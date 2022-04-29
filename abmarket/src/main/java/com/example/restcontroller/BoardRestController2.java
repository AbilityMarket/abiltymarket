package com.example.restcontroller;

import java.util.HashMap;
import java.util.Map;

import com.example.jwt.JwtUtil;
import com.example.service.BoardService1;
import com.example.service.ChatService2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/board")
public class BoardRestController2 {

    @Autowired
    BoardService1 bService1;

    @Autowired
    JwtUtil jwtUtil;

    // 취합하실때 이것도 같이 부탁드려요!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    @Autowired
    ChatService2 cService2;

    // 127.0.0.1:9090/ROOT/api/chat/chatcount?bno=2
    // 게시글 채팅 개수 조화
    @RequestMapping(value = "/chatcount", method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> chatcount(
            @RequestParam(name = "bno") Long bno) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            Long count = cService2.chatcount(bno);
            if (count != null) {
                map.put("status", 200);
                map.put("count", count);
            }
        } catch (Exception e) {
            map.put("status", -1);
            e.printStackTrace();
        }
        return map;
    }
}
