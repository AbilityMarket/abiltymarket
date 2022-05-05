package com.example.restcontroller;

import java.util.HashMap;
import java.util.Map;

import com.example.entity.BoardEntity;
import com.example.entity.BolikeEntity;
import com.example.entity.MemberEntity;
import com.example.jwt.JwtUtil;
import com.example.repository.BolikeRepository3;
import com.example.service.BolikeService3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/bolike")
public class BolikeRestController3 {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    BolikeService3 bolikeService3;

    @Autowired
    BolikeRepository3 bolikeRepository3;

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
        @RequestBody BolikeEntity bolike) {
        
        Map<String, Object> map = new HashMap<>();

        try {
            //토큰 필요함(토큰 추출)
            String userid = jwtUtil.extractUsername(token);
            System.out.println("RequestMapping username : " + userid);

            Long bno = bolike.getBoard().getBno();

            // db 유무 확인
            int bEntity = bolikeService3.chkBolike(userid, bno);
            System.out.println("bEntity==="+bEntity);
            // db에 있으면 0, 없으면 1 (찜등록)
            //{"member" : {"uid":"dd"}, "board" : {"bno":"3"}}

            if(bEntity == 1) {
                MemberEntity memberEntity = new MemberEntity();
                memberEntity.setUid(userid);
                bolike.setMember(memberEntity);
                System.out.println(memberEntity);

                BoardEntity boardEntity = new BoardEntity();
                boardEntity.setBno(bno);
                bolike.setBoard(boardEntity);
                System.out.println(boardEntity);
                        
                int ret = bolikeService3.insertBolike(bolike);
                if(ret == 1) {
                    map.put("status", 200);
                    map.put("result", "찜등록!");
                }
            }
            else if(bEntity == 0) {
                map.put("status", 0);
                map.put("status", "이미찜");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }
    //왜 2번 등록이 될까나ㅏ나ㅏㅏㅏㅏㅏ 수정하자ㅏㅏㅏㅏㅏㅏㅏ

    //삭제 에러 수정하기ㅣㅣㅣ이ㅣ이
    //java.lang.Integer is in module java.base of loader 'bootstrap'

    // 찜 취소(삭제) (토큰 = 찜 등록한 사람)
    // 찜 유무 먼저 확인
    // 127.0.0.1:9090/ROOT/api/bolike/unlike
    @RequestMapping(value = {"/unlike"},
        method = {RequestMethod.DELETE},
        consumes = {MediaType.ALL_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public Map<String, Object> unlikeDELETE(
        @RequestHeader(name = "token") String token,
        @RequestBody BolikeEntity bolike) {

        Map<String, Object> map = new HashMap<>();

        try {
            //토큰 필요함(토큰 추출)
            String userid = jwtUtil.extractUsername(token);
            System.out.println("RequestMapping username : " + userid);

            Long bno = bolike.getBoard().getBno();
            //Long bolno = bolike.getBolno();

            // db 유무 확인
            int bEntity = bolikeService3.chkBolike(userid, bno);
            System.out.println("bEntity==="+bEntity);
            // db에 있으면 0(찜취소), 없으면 1

            if(bEntity == 0) {
                //토큰 = 찜 등록한 사람

                int ret = bolikeService3.deleteBolike(userid, bno);
                if(ret == 1) {
                    map.put("result", "찜취소");
                    map.put("status", 200);
                }
            }
            else if(bEntity == 1) {
                map.put("status", 0);
                map.put("result", "이미취소");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }
}
