package com.example.restcontroller;

import java.util.HashMap;
import java.util.Map;

import com.example.entity.InterestEntity;
import com.example.entity.MemberEntity;
import com.example.entity.MeminterestEntity;
import com.example.jwt.JwtUtil;
import com.example.repository.MemInterestRepository1;
import com.example.service.MemInterestService1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/api/meminterest")
@RestController
public class MemInterestRestController1 {

    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    MemInterestService1 memIntService1;

    @Autowired
    MemInterestRepository1 memIntRepository1;

    // 관심사 알람설정
    @RequestMapping(value = { "/mialert" }, method = { RequestMethod.POST }, consumes = {
            MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> insertPOST(
            @RequestHeader(name = "token") String token,
            @RequestBody MeminterestEntity memIEntity,
            @RequestParam(name = "incode") long incode
    // ,@RequestParam(name = "micode") long micode
    ) {
        Map<String, Object> map = new HashMap<>();

        try {
            // 토큰 사용
            String userid = jwtUtil.extractUsername(token);
            System.out.println("userid =>" + userid);

            // memIntRepository1.findById(micode);

            // 회원 연결
            MemberEntity memEntity = new MemberEntity();
            memEntity.setUid(userid);
            System.out.println("memberEntity =>" + memEntity);

            memIEntity.setMember(memEntity);
            System.out.println(memIEntity.toString());

            InterestEntity intEntity = new InterestEntity();
            intEntity.setIncode(incode);
            memIEntity.setInterest(intEntity);

            // 0 또는 1로 설정
            // 0일경우 알람을 받지 않는다. 1일경우 알람을 받는다.

            int ret = memIntService1.insertalert(memIEntity);
            if (ret == 1) {
                map.put("status", 200);
                // map.put("msg", "알람이 설정되었습니다.");
            } else {
                map.put("status", 0);
            }

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // 관심사알람 설정
    @RequestMapping(value = { "/alert" }, method = { RequestMethod.POST }, consumes = {
            MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> alertPOST(
            @RequestHeader(name = "token") String token,
            @RequestBody MeminterestEntity memIEntity,
            @RequestParam(name = "incode") long incode
    // ,@RequestParam(name = "micode") long micode
    ) {
        Map<String, Object> map = new HashMap<>();

        try {
            // 토큰 사용
            String userid = jwtUtil.extractUsername(token);
            System.out.println("userid =>" + userid);

            // memIntRepository1.findById(micode);

            // 회원 연결
            MemberEntity memEntity = new MemberEntity();
            memEntity.setUid(userid);
            System.out.println("memberEntity =>" + memEntity);

            memIEntity.setMember(memEntity);
            System.out.println(memIEntity.toString());

            InterestEntity intEntity = new InterestEntity();
            intEntity.setIncode(incode);
            memIEntity.setInterest(intEntity);

            // 0 또는 1로 설정
            // 0일경우 알람을 받지 않는다. 1일경우 알람을 받는다.

            int ret = memIntService1.insertalert(memIEntity);
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

}
