package com.example.restcontroller;

import java.util.HashMap;
import java.util.Map;

import com.example.entity.BoardEntity;
import com.example.entity.BolikeEntity;
import com.example.entity.MemberEntity;
import com.example.jwt.JwtUtil;
import com.example.service.BolikeService3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/bolike")
public class BolikeRestController3 {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    BolikeService3 bolikeService3;

    // 찜 등록(토큰 필요)
    // 찜 유무 먼저 확인
    // 127.0.0.1:9090/ROOT/api/bolike/like
    @RequestMapping(value = {"/like"},
        method = {RequestMethod.POST},
        consumes = {MediaType.ALL_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public Map<String, Object> likePOST(
        @RequestHeader(name = "token") String token,
        @RequestBody BolikeEntity bolike,
        @RequestParam(name = "bno") long bno) {
        
        Map<String, Object> map = new HashMap<>();

        try {
            //토큰 필요함(토큰 추출)
            String userid = jwtUtil.extractUsername(token);
            System.out.println("RequestMapping username : " + userid);
            
            // 유뮤 확인
            Long result = bolikeService3.chkBolike(userid, bno);
            System.out.println(result); //null
            if(result != null) {
                MemberEntity memberEntity = new MemberEntity();
                memberEntity.setUid(userid);
                System.out.println(memberEntity);
    
                bolike.setMember(memberEntity);
                System.out.println(bolike);
    
                BoardEntity boardEntity = new BoardEntity();
                boardEntity.setBno(bno);
                System.out.println(boardEntity);
    
                bolike.setBoard(boardEntity);
                System.out.println(bolike);
    
                map.put("status", 200);
            }
            map.put("status", 0);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }
    
}
