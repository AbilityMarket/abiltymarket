package com.example.restcontroller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.entity.BoardEntity;
import com.example.entity.BoardImageEntity;
import com.example.jwt.JwtUtil;
import com.example.service.BoardImageService1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/api/boardimg")
public class BoardImageRestController1 {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    BoardImageService1 boardimgService1;

    // 이미지 등록
    @RequestMapping(value = "/insert", method = { RequestMethod.POST }, consumes = {
            MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> insertPost(
            @RequestHeader(name = "token") String token,
            @ModelAttribute BoardImageEntity boardimage,
            @RequestParam(name = "file") MultipartFile[] file,
            @RequestParam(name = "bno") long bno) {

        Map<String, Object> map = new HashMap<>();

        try {
            String userid = jwtUtil.extractUsername(token);
            System.out.println("TOKEN :" + userid);

            List<BoardImageEntity> list = new ArrayList<>();

            for (int i = 0; i < file.length; i++) {
                if (file != null) {
                    BoardImageEntity boardImg = new BoardImageEntity();
                    boardImg.setBimage(file[i].getBytes());
                    boardImg.setBimagename(file[i].getOriginalFilename());
                    boardImg.setBimagesize(file[i].getSize());
                    boardImg.setBimagetype(file[i].getContentType());

                    list.add(boardImg);
                    boardimgService1.insertBoardImage(list);

                    BoardEntity bEntity = new BoardEntity();
                    bEntity.setBno(bno);

                    int ret = boardimgService1.insertBoardImage(list);
                    if (ret == 1) {
                        map.put("status", 200);
                    } else {
                        map.put("status", 0);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // 127.0.0.1:9090/ROOT/api/boardimg/selectone?bino=1
    // 이미지 가져오기
    @RequestMapping(value = "/selectone", method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> selectOneGET(@RequestParam(name = "bino") long bino) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);

        try {
            BoardImageEntity retBoardI = boardimgService1.selectBoardImageOne(bino);
            if (retBoardI != null) {
                map.put("status", 200);
                map.put("result", retBoardI.getBino());
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // 이미지 수정
    @RequestMapping(value = "/update", method = { RequestMethod.PUT }, consumes = {
            MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> updatePost(
            @RequestHeader(name = "token") String token,
            @ModelAttribute BoardImageEntity boardimage,
            @RequestParam(name = "file") MultipartFile file, Long bno) {
        Map<String, Object> map = new HashMap<>();

        try {
            String userid = jwtUtil.extractUsername(token);
            System.out.println("TOKEN :" + userid);

            int ret = boardimgService1.updateBoardImage(boardimage);
            if (ret == 1) {
                map.put("status", 200);
            }

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // 이미지 삭제
    @RequestMapping(value = "/delete", method = { RequestMethod.DELETE }, consumes = {
            MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> deletePost(
            @RequestHeader(name = "token") String token,
            @RequestParam(name = "bino") long bino) {

        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);

        try {
            String userid = jwtUtil.extractUsername(token);
            System.out.println("TOKEN :" + userid);

            int ret = boardimgService1.deleteBoardImage(bino);
            if (ret == 1) {
                map.put("status", 200);
            }

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // 127.0.0.1:9090/ROOT/api/boardimg/selectlist
    // 이미지 가져오기(여러개)

}
