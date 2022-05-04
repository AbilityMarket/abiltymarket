package com.example.restcontroller;

import java.util.HashMap;
import java.util.Map;

import com.example.entity.BoardEntity;
import com.example.entity.MemberEntity;
import com.example.entity.ReportEntity;
import com.example.jwt.JwtUtil;
import com.example.service.ReportService1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/report")
public class ReportRestController1 {

    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    ReportService1 repservice1;

    // 신고글 작성
    // 127.0.0.1:9090/ROOT/api/report/insert
    @RequestMapping(value = { "/insert" }, method = { RequestMethod.POST }, consumes = {
            MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> insertPost(
            @RequestHeader(name = "token") String token,
            @ModelAttribute ReportEntity rEntity,
            @RequestParam(name = "bno") long bno) {

        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        System.out.println(token);
        System.out.println(rEntity.toString());

        try {
            String userid = jwtUtil.extractUsername(token);
            System.out.println(userid);

            MemberEntity mEntity = new MemberEntity();
            mEntity.setUid(userid);
            rEntity.setMember(mEntity);
            // System.out.println("mEntity => " + mEntity.toString());

            BoardEntity bEntity = new BoardEntity();
            bEntity.setBno(bno);
            rEntity.setBoard(bEntity);
            // System.out.println("bEntity => " + bEntity.toString());

            int let = repservice1.insertReport(rEntity);
            if (let == 1) {

                map.put("status", 200);
                map.put("msg", "신고글이 작성되었습니다.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;

    }

    // 조회
    // 127.0.0.1:9090/ROOT/api/report/select?repcode=1
    @RequestMapping(value = "/select", method = { RequestMethod.GET }, consumes = { MediaType.ALL_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> selectOneGET(@RequestParam(name = "repcode") long repcode) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);

        ReportEntity retReport = repservice1.selectReport(repcode);
        if (retReport != null) {
            map.put("status", 200);
            map.put("repcode", repcode);
        }
        return map;
    }

    // 수정
    // 127.0.0.1:9090/ROOT/api/report/update?repcode=1
    @RequestMapping(value = "/update", method = { RequestMethod.PUT }, consumes = { MediaType.ALL_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> updatePost(
            @RequestHeader(name = "token") String token,
            @ModelAttribute ReportEntity rEntity) {

        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        System.out.println("TOKEN :" + token);
        System.out.println("rEntity :" + rEntity.toString());

        try {
            String userid = jwtUtil.extractUsername(token);
            System.out.println(userid);

            ReportEntity rEntity1 = repservice1.selectReport(rEntity.getRepcode());
            System.out.println(rEntity1.toString());
            rEntity1.setReptitle(rEntity.getReptitle());
            rEntity1.setRepcontent(rEntity.getRepcontent());
            rEntity1.setRetype(rEntity.getRetype());

            int ret = repservice1.updateReport(rEntity1);
            if (ret == 1) {
                map.put("status", 200);
                map.put("msg", "신고글이 수정되었습니다.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // 삭제
    // 127.0.0.1:9090/ROOT/api/report/delete?repcode=1
    @RequestMapping(value = "/delete", method = { RequestMethod.DELETE }, consumes = {
            MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> deletePost(
            @RequestHeader(name = "token") String token,
            @RequestParam(name = "repcode") long repcode) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);

        try {
            String userid = jwtUtil.extractUsername(token);
            System.out.println(userid);

            int ret = repservice1.deleteReport(repcode);
            if (ret == 1) {
                map.put("status", 200);
                map.put("msg", "신고글이 삭제되었습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

}
