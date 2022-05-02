package com.example.restcontroller;

import java.util.HashMap;
import java.util.Map;

import com.example.entity.InquireEntity;
import com.example.entity.MemberEntity;
import com.example.jwt.JwtUtil;
import com.example.repository.InquireRepository3;
import com.example.service.InquireService1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/inquire")

public class InquireRestController3 {

    // 토큰
    @Autowired JwtUtil jwtUtil;

    @Autowired
    InquireService1 inqService1;

    @Autowired
    InquireRepository3 inqRepository3;

    // 문의글 등록 (토큰 필요)
    // 127.0.0.1:9090/ROOT/api/inquire/insert
    @RequestMapping(value = {"/insert"},
        method = {RequestMethod.POST},
        consumes = {MediaType.ALL_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public Map<String, Object> insertPOST(
        @RequestHeader(name = "token") String token,
        @RequestBody InquireEntity inquireEntity) {
        
        Map<String, Object> map = new HashMap<>();

        try {
            //토큰 필요함(토큰 추출)
            String userid = jwtUtil.extractUsername(token);
            System.out.println("RequestMapping username : " + userid);

            MemberEntity memberEntity = new MemberEntity();
            memberEntity.setUid(userid);
            System.out.println(memberEntity);

            inquireEntity.setMember(memberEntity);
            System.out.println(inquireEntity.toString());

            int ret = inqService1.insertInquire(inquireEntity);
            if(ret == 1) {
                map.put("result", "등록완료!");
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

    // 문의글 삭제 (작성자와 동일)
    // 127.0.0.1:9090/ROOT/api/inquire/deleteone
    @RequestMapping(value = {"/deleteone"},
        method = {RequestMethod.DELETE},
        consumes = {MediaType.ALL_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public Map<String, Object> deleteOne(
        @RequestHeader(name = "token") String token,
        @RequestParam(name = "inqno") long inqno) {

        Map<String, Object> map = new HashMap<>();
        
        try {            
            //토큰 필요함(토큰 추출)
            String userid = jwtUtil.extractUsername(token);
            System.out.println("RequestMapping username : " + userid);

            //게시판 글번호 추출
            InquireEntity inquireEntity = inqRepository3.getById(inqno);

            if(userid.equals(inquireEntity.getMember().getUid())) {
                int ret = inqService1.deleteOneInquire(inqno);
                if(ret == 1) {
                    map.put("result", "삭제완료!");
                    map.put("status", 200);
                }
            }
            else {
                map.put("result", "작성자X!");
                map.put("status", 0);                
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // 문의글 전체 목록 조회 (작성자와 동일)
    // 127.0.0.1:9090/ROOT/api/inquire/selectlist


    // 문의글 1개 조회 (작성자와 동일)
    // 127.0.0.1:9090/ROOT/api/inquire/selectone

    // 문의글 수정 (작성자와 동일)
    // 127.0.0.1:9090/ROOT/api/inquire/updateone



}
