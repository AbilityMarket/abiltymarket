package com.example.restcontroller;

import java.util.HashMap;
import java.util.Map;

import com.example.entity.BoardEntity;
import com.example.service.BoardImageService1;
import com.example.service.BoardService1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/Board")
public class BoardRestController1 {

    @Autowired
    BoardService1 bService1;

    @Autowired
    BoardImageService1 biService1;

    // 게시글 작성
    @RequestMapping(value = "/insert", method = { RequestMethod.POST }, consumes = { MediaType.ALL_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> insertPost(@RequestBody BoardEntity bEntity) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);

        int ret = bService1.insertBoard(bEntity);
        if (ret == 1) {
            map.put("status", 200);
        }
        return map;
    }

    // 게시글 삭제
    @RequestMapping(value = "/delete", method = { RequestMethod.DELETE }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> deletePost(@RequestParam(name = "bno") long bno) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        int ret = bService1.deleteBoardOne(bno);
        if (ret == 1) {
            map.put("status", 200);
        }
        return map;
    }

    // 게시글 수정
    @RequestMapping(value = "/update", method = { RequestMethod.PUT }, consumes = { MediaType.ALL_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> updatePost(@RequestBody BoardEntity bentity) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);

        int ret = bService1.updateBoardOne(bentity);
        if (ret == 1) {
            map.put("status", 200);
        }
        return map;
    }

}
