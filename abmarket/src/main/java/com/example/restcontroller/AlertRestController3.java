package com.example.restcontroller;

import java.util.HashMap;
import java.util.Map;

import com.example.entity.AlertEntity;
import com.example.entity.MemberEntity;
import com.example.jwt.JwtUtil;
import com.example.service.AlertService3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/alert")

public class AlertRestController3 {

    // 토큰
    @Autowired JwtUtil jwtUtil;

    @Autowired AlertService3 alService3;

    // 알람 등록(토큰 필요)
    // 알람 종류 설정 필요
    // 127.0.0.1:9090/ROOT/api/alert/insert
    @RequestMapping(value = {"/insert"},
        method = {RequestMethod.POST},
        consumes = {MediaType.ALL_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public Map<String, Object> insetPOST(
        @RequestHeader(name = "token") String token,
        @RequestBody AlertEntity alertet) {

        Map<String, Object> map = new HashMap<>();

        try {
            //토큰 필요함(토큰 추출)
            String userid = jwtUtil.extractUsername(token);
            System.out.println("RequestMapping username : " + userid);
            
            //작성 회원 연결하기
            MemberEntity memberEntity = new MemberEntity();
            memberEntity.setUid(userid);
            System.out.println(memberEntity);

            alertet.setMember(memberEntity);
            System.out.println(alertet.toString());

            //알람종류 설정하기
            //1-게시판댓글(comm), 2-게시판대댓글(recomm), 3-문의답변(answer), 4-후기(review)
            //등급, 신고 알람
            


            int ret = alService3.insertAlert(alertet);
            if(ret == 1) {
                map.put("status", 200);
            }
            else {
                map.put("status", 0);
            }

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // 알람 읽은 여부 설정()
    // Boolean(Object)의 기본값은 null
    // boolean(primitive)의 기본값은 false.
    // 127.0.0.1:9090/ROOT/api/alert/alreadchk
    @RequestMapping(value = {"/alreadchk"},
        method = {RequestMethod.GET},
        consumes = {MediaType.ALL_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public Map<String, Object> alreadGET(
        @RequestHeader(name = "token") String token,
        @RequestParam(name = "alno") long alno) {

        Map<String, Object> map = new HashMap<>();

        try {
            //토큰 필요함(토큰 추출)
            String userid = jwtUtil.extractUsername(token);
            System.out.println("RequestMapping username : " + userid);

            //AlertService3.alertReadChk(Long alno) : Boolean
            boolean alrchk = alService3.alertReadChk(alno);
            System.out.println(alrchk); //null
            
            if(alrchk != false) {
                map.put("read", alrchk);
                map.put("status", 200);
            }
            else {
                map.put("result", "아직읽지않음");
                map.put("status", 0);
            }
            // map.put("status", 200);
        }
        catch (Exception e) {
            map.put("status", -1);
            e.printStackTrace();
        }
        return map;
        

    }



}
