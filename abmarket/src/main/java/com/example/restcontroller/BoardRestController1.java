package com.example.restcontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.entity.BoardEntity;
import com.example.entity.BoardImageEntity;
import com.example.jwt.JwtUtil;
import com.example.service.BoardImageService1;
import com.example.service.BoardService1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/api/board")
public class BoardRestController1 {

    @Autowired
    BoardService1 bService1;

    @Autowired
    BoardImageService1 biService1;

    @Autowired
    JwtUtil jwtUtil;

    // 게시글 작성
    @RequestMapping(value = "/insert", method = { RequestMethod.POST }, consumes = { MediaType.ALL_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> insertPost(
            @RequestBody BoardEntity bEntity,
            @RequestBody BoardImageEntity biEntity,
            @RequestHeader(name = "token") String token,
            @RequestParam(name = "file") MultipartFile file) {

        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);

        try {
            String userid = jwtUtil.extractUsername(token);
            System.out.println(userid);

            biEntity.setBimage(file.getBytes());
            biEntity.setBimagename(file.getOriginalFilename());
            biEntity.setBimagesize(file.getSize());
            biEntity.setBimagetype(file.getContentType());

            int ret = bService1.insertBoard(bEntity);
            if (ret == 1) {
                map.put("status", 200);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);

        }
        return map;
    }

    // 게시글 삭제
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
    @RequestMapping(value = "/update", method = { RequestMethod.PUT }, consumes = { MediaType.ALL_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> updatePost(
            @RequestHeader(name = "token") String token,
            @RequestBody BoardEntity bentity) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);

        try {
            String username = jwtUtil.extractUsername(token);
            System.out.println(username);

            int ret = bService1.updateBoardOne(bentity);
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
    @RequestMapping(value = "/selectone", method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> selectOneGET(@RequestParam(name = "bno") long bno) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);

        BoardEntity retBoard = bService1.selectBoardOne(bno);
        if (retBoard != null) {
            map.put("status", 200);
            map.put("result", retBoard);
        }
        return map;
    }

    // 게시판 목록(페이지네이션)
    // @RequestMapping(value = "selectlist", method = { RequestMethod.GET },
    // consumes = {
    // MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    // public Map<String, Object> boardSelectlistPost(
    // @RequestParam(name = "page") int page) {

    // Map<String, Object> map = new HashMap<>();
    // map.put("status", 0);

    // try{
    // List<BoardEntity> list = bService1.selectListBoard(
    // (page * 10) - (10 - 1), page * 10);
    // if (list != null) {
    // map.put("status", 200);
    // map.put("result", list);
    // }
    // }
    // catch(Exception e) {
    // e.printStackTrace();
    // map.put("status", -1);
    // }
    // return map;
    // }
}
