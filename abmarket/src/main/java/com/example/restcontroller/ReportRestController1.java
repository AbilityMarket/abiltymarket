package com.example.restcontroller;

import java.util.HashMap;
import java.util.Map;

import com.example.entity.MemberEntity;
import com.example.entity.ReportEntity;
import com.example.jwt.JwtUtil;
import com.example.service.ReportService1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
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

    // 작성
    @RequestMapping(value = { "/insert" }, method = { RequestMethod.POST }, consumes = {
            MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> insertPost(
            @RequestHeader(name = "token") String token,
            @ModelAttribute ReportEntity report) {

        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);

        try {
            String userid = jwtUtil.extractUsername(token);
            System.out.println(userid);

            int let = repservice1.insertReport(report);
            if (let == 1) {

                map.put("status", 200);
            }

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;

    }

    // 조회
    @RequestMapping(value = "/select", method = { RequestMethod.GET }, consumes = { MediaType.ALL_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> selectOneGET(@RequestParam(name = "repcode") long repcode) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);

        ReportEntity retReport = repservice1.selectReport(repcode);
        if (retReport != null) {
            map.put("status", 200);
            map.put("status", retReport);
        }
        return map;
    }

    // 수정
    // @RequestMapping(value = "/update", method = { RequestMethod.PUT }, consumes =
    // { MediaType.ALL_VALUE }, produces = {
    // MediaType.APPLICATION_JSON_VALUE })
    // public Map<String, Object> updatePost(
    // @RequestHeader(name = "token") String token,
    // @RequestBody ReportEntity report){

    // Map<String, Object> map = new HashMap<>();
    // map.put("status", 0);

    // try{
    // String username = jwtUtil.extractUsername(token);
    // System.out.println(username);

    // MemberEntity member = new MemberEntity();
    // member.

    // }
    // catch(Exception e) {
    // e.printStackTrace();
    // map.put("status", -1);
    // }
    // return map;
    // }

    // 삭제

}
