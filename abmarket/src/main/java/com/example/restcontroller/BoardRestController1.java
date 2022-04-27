package com.example.restcontroller;

import java.util.HashMap;
import java.util.Map;

import javax.swing.Spring;

import com.example.entity.BoardEntity;
import com.example.entity.MemberEntity;
import com.example.jwt.JwtUtil;
import com.example.service.BoardService1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.bytebuddy.implementation.bytecode.constant.DefaultValue;

@RestController
@RequestMapping(value = "/api/board")
public class BoardRestController1 {

    @Autowired
    BoardService1 bService1;

    @Autowired
    JwtUtil jwtUtil;

    // 게시글 작성
    // 127.0.0.1:9090/ROOT/api/board/insert
    @RequestMapping(value = "/insert", method = { RequestMethod.POST }, consumes = { MediaType.ALL_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> insertPost(
            @ModelAttribute BoardEntity bEntity,
            @RequestHeader(name = "token") String token) {

        Map<String, Object> map = new HashMap<>();

        try {
            String userid = jwtUtil.extractUsername(token);
            System.out.println("TOKEN :" + userid);

            MemberEntity mEntity = new MemberEntity();
            mEntity.setUid(userid);

            bEntity.setMember(mEntity);
            System.out.println("bEntity =>" + bEntity.toString());

            int ret = bService1.insertBoard(bEntity);
            if (ret == 1) {
                map.put("status", 200);
            } else {
                map.put("status", 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);

        }
        return map;
    }

    // 게시글 삭제
    // 127.0.0.1:9090/ROOT/api/board/delete
    @RequestMapping(value = "/delete", method = { RequestMethod.DELETE }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> deletePost(
            @RequestHeader(name = "token") String token,
            @RequestParam(name = "bno") long bno) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);

        try {
            String username = jwtUtil.extractUsername(token);
            System.out.println(username);

            int ret = bService1.deleteBoardOne(bno);
            if (ret == 1) {
                map.put("status", 200);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // 게시글 수정
    // 127.0.0.1:9090/ROOT/api/board/update
    @RequestMapping(value = "/update", method = { RequestMethod.PUT }, consumes = { MediaType.ALL_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> updatePost(
            @RequestHeader(name = "token") String token,
            @ModelAttribute BoardEntity bEntity) {

        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        System.out.println(token);
        System.out.println(bEntity.toString());

        try {
            String userid = jwtUtil.extractUsername(token);
            System.out.println("TOKEN :" + userid);

            BoardEntity bEntity1 = bService1.selectBoardOne(bEntity.getBno());
            System.out.println(bEntity1.toString());
            bEntity1.setBtitle(bEntity.getBtitle());
            bEntity1.setBcontent(bEntity.getBcontent());
            bEntity1.setBprice(bEntity.getBprice());

            int ret = bService1.updateBoardOne(bEntity1);
            if (ret == 1) {
                map.put("status", 200);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // 게시글 조회
    // 127.0.0.1:9090/ROOT/api/board/selectone
    @RequestMapping(value = "/selectone", method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> selectOneGET(@RequestParam(name = "bno") long bno) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);

        try {
            BoardEntity retBoard = bService1.selectBoardOne(bno);
            if (retBoard != null) {
                map.put("status", 200);
                map.put("result", retBoard);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("value", -1);
        }
        return map;
    }

    // 게시판 목록(페이지네이션)
    // 127.0.0.1:9090/ROOT/api/board/selectlist
    // @RequestMapping(value = "/selectlist", method = { RequestMethod.GET },
    // consumes = {
    // MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    // public Map<String, Object> boardSelectListPost(
    // @RequestParam(name = "page", defaultValue = "1") int page,
    // @RequestParam(name = "text", defaultValue = "") Spring text) {

    // Map<String, Object> map = new HashMap<>();
    // map.put("status", 0);

    // try {
    // List<BoardEntity> list = bService1.selectListBoard(
    // (page * 10) - (10 - 1), page * 10);
    // if (list != null) {
    // map.put("status", 200);
    // map.put("result", list);
    // }
    // } catch (Exception e) {
    // e.printStackTrace();
    // map.put("status", -1);
    // }
    // return map;
    // }
}
