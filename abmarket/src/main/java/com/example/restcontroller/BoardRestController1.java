package com.example.restcontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.entity.BoardEntity;
import com.example.entity.MemberEntity;
import com.example.jwt.JwtUtil;
import com.example.service.BoardService1;
import com.example.service.ChatService2;
import com.example.service.CommService2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/board")
public class BoardRestController1 {

    @Autowired
    BoardService1 bService1;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    ChatService2 cService2;

    @Autowired
    CommService2 commService2;

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
            System.out.println("userid =>" + userid);

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
        System.out.println("TOKEN :" + token);
        System.out.println("bEntity :" + bEntity.toString());

        try {
            String userid = jwtUtil.extractUsername(token);
            System.out.println(userid);

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
    @RequestMapping(value = { "/selectlist" }, method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> boardSelectListGET(
            @RequestParam(value = "title", defaultValue = "") String btitle,
            @RequestParam(value = "page", defaultValue = "0") int page) {

        Map<String, Object> map = new HashMap<>();

        Pageable pageable = PageRequest.of(page - 1, 10);

        try {
            List<BoardEntity> list = bService1.selectListBoard(pageable, btitle);

            if (list != null) {
                map.put("title", btitle);
                map.put("page", page);

                map.put("status", 200);
                map.put("result", list);
            } else {
                map.put("status", 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // 127.0.0.1:9090/ROOT/api/board/chatcount?bno=2
    // 게시글 채팅 개수 조회
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

    // 127.0.0.1:9090/ROOT/api/board/commcount?bno=2
    // 게시글 댓글 개수 조회
    @RequestMapping(value = "/commcount", method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> commcount(
            @RequestParam(name = "bno") Long bno) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            Long count = commService2.countComm(bno);
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
