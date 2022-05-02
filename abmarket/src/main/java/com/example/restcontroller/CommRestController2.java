package com.example.restcontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.entity.BoardEntity;
import com.example.entity.CommEntity;
import com.example.entity.MemberEntity;
import com.example.entity.RecommentEntity;
import com.example.jwt.JwtUtil;
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
@RequestMapping(value = "/api/comm")
public class CommRestController2 {

    @Autowired
    CommService2 cService2;

    @Autowired
    JwtUtil jwtUtil;

    // 댓글 쓰기
    @RequestMapping(value = "/insert", method = { RequestMethod.POST }, consumes = { MediaType.ALL_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> insertPost(
            @RequestParam(name = "bno") Long bno,
            @ModelAttribute CommEntity comm,
            @RequestHeader(name = "token") String token) {

        Map<String, Object> map = new HashMap<>();

        try {
            String userid = jwtUtil.extractUsername(token);
            System.out.println("userid =>" + userid);

            MemberEntity mEntity = new MemberEntity();
            mEntity.setUid(userid);

            BoardEntity board = new BoardEntity();
            board.setBno(bno);

            // comm에 지금 로그인한 사람 넣기
            comm.setMember(mEntity);
            comm.setBoard(board);

            int ret = cService2.insertComm(comm);
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

    // 댓글 삭제
    // 127.0.0.1:9090/ROOT/api/comm/delete
    @RequestMapping(value = "/delete", method = { RequestMethod.DELETE }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> deletePost(
            @RequestParam(name = "cono") Long cono,
            @RequestHeader(name = "token") String token) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);

        try {
            String uid = jwtUtil.extractUsername(token);

            int ret = cService2.deleteComm(uid, cono);

            if (ret == 1) {
                map.put("status", 200);
                map.put("msg", "삭제 완료");
            }
            if (ret == 0) {
                map.put("msg", "본인 댓글 아님");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // 게시글 수정
    // 127.0.0.1:9090/ROOT/api/comm/update
    @RequestMapping(value = "/update", method = { RequestMethod.PUT }, consumes = { MediaType.ALL_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> updatePut(
            @RequestHeader(name = "token") String token,
            @ModelAttribute CommEntity comm) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);

        try {
            String uid = jwtUtil.extractUsername(token);

            int ret = cService2.updateComm(uid, comm);
            if (ret == 1) {
                map.put("status", 200);
            }

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // 댓글 목록 조회
    @RequestMapping(value = { "/selectlist" }, method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> boardSelectListGET(
            @RequestParam(value = "bno") Long bno,
            @RequestParam(value = "page", defaultValue = "1") int page) {

        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        Pageable pageable = PageRequest.of(page - 1, 10);

        try {
            List<CommEntity> list = cService2.selectListComm(pageable, bno);

            if (list != null) {
                map.put("status", 200);
                map.put("list", list);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // 자기가 쓴 글 표시하기
    // 127.0.0.1:9090/ROOT/api/comm/checkMine?cono=1
    @RequestMapping(value = { "/checkMine" }, method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> checkMineGET(
            @RequestParam(name = "cono") Long cono,
            @RequestHeader(name = "token") String token) {

        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);

        try {
            String uid = jwtUtil.extractUsername(token);
            int ret = cService2.checkMine(cono, uid);
            if (ret == 1) {
                map.put("status", 200);
            }

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // 대댓글 쓰기
    @RequestMapping(value = "/insertRecomment", method = { RequestMethod.POST }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> insertRecomment(
            @RequestParam(name = "bno") Long bno,
            @ModelAttribute RecommentEntity recomment,
            @RequestHeader(name = "token") String token) {

        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            System.out.println(recomment);

            String uid = jwtUtil.extractUsername(token);
            if (recomment.getComm().getMember().getUid().equals(uid)) {
                int ret = cService2.insertRecomm(recomment);
                if (ret == 1) {
                    map.put("status", 200);
                }
            }
            map.put("status", -2);
            map.put("msg", "token아이디가 글쓴이가 아님");

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);

        }
        return map;
    }

}
