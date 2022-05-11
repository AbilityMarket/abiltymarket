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

    // 관심사 알람설정 여부 확인 후 등록
    // 127.0.0.1:9090/ROOT/api/meminterest/insertalert
    @RequestMapping(value = { "/insertalert" }, method = { RequestMethod.POST }, consumes = {
            MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> insertalerttPOST(
            @RequestHeader(name = "token") String token,
            @RequestBody MeminterestEntity meminterest) {
        Map<String, Object> map = new HashMap<>();

        try {
            // 토큰 사용
            String userid = jwtUtil.extractUsername(token);
            System.out.println("userid =>" + userid);

            Long mialert = meminterest.getMialert();
            Long micode = meminterest.getMicode();

            int alert = memIntService1.chkalertinterest(userid, mialert);
            System.out.println(mialert);

            // 알림 설정 확인 후 미설정시 않을 경우 설정을 해준다.
            if (alert == 0) {
                // 회원 연결
                MemberEntity memEntity = new MemberEntity();
                memEntity.setUid(userid);
                meminterest.setMember(memEntity);

                int ret = memIntService1.insertalert(meminterest);
                if (ret == 1) {
                    map.put("result", "알람 설정 완료");
                    map.put("status", 200);
                } else {
                    map.put("status", 0);
                }
            } // 알림 설정이 되어 있을경우 설정을 해제한다.
            else if (alert == 1) {

                int ret = memIntService1.deletealert(micode);
                if (ret == 1) {
                    map.put("result", "알림 해제 완료");
                    map.put("status", 200);
                } else {
                    map.put("status", 0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // 회원 관심사 등록
    // 127.0.0.1:9090/ROOT/api/meminterest/mialert?incode=5
    @RequestMapping(value = { "/mialert" }, method = { RequestMethod.POST }, consumes = {
            MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> insertPOST(
            @RequestHeader(name = "token") String token,
            @RequestParam(name = "incode") long incode[]) {
        Map<String, Object> map = new HashMap<>();

        try {
            // 토큰 사용
            String userid = jwtUtil.extractUsername(token);
            System.out.println("userid =>" + userid);

            // 회원 연결
            MemberEntity memEntity = new MemberEntity();
            memEntity.setUid(userid);
            System.out.println("memberEntity =>" + memEntity);

            int ret = 0;
            for (int i = 0; i < incode.length; i++) {
                MeminterestEntity memIEntity = new MeminterestEntity();
                memIEntity.setMember(memEntity);
                InterestEntity interest = new InterestEntity();
                interest.setIncode(incode[i]);
                memIEntity.setInterest(interest);
                System.out.println(memIEntity.toString());

                int ret1 = memIntService1.insertinterest(memIEntity);
                ret += ret1;
            }
            if (ret == incode.length) {
                map.put("status", 200);
                map.put("reslut", "관심사 등록");
                // map.put("msg", "관심사가 등록되었습니다.");
            } else {
                map.put("status", 0);
            }

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // 회원 관심사 등록해제
    // 127.0.0.1:9090/ROOT/api/meminterest/mialertoff?incode=1
    @RequestMapping(value = { "/mialertoff" }, method = { RequestMethod.DELETE }, consumes = {
            MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> insert1POST(
            @RequestHeader(name = "token") String token,
            @RequestParam(name = "incode") long incode) {
        Map<String, Object> map = new HashMap<>();
        System.out.println(incode);

        try {
            // 토큰 사용
            String userid = jwtUtil.extractUsername(token);
            System.out.println("userid =>" + userid);

            int ret = memIntService1.deleteinterest(userid, incode);
            if (ret == 1) {
                map.put("status", 200);
                map.put("reslut", "관심사 해제");
                // map.put("msg", "관심사가 해제되었습니다.");
            } else {
                map.put("status", 0);
            }

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // On Off 유무 확인
    // 127.0.0.1:9090/ROOT/api/meminterest/chkalert
    // @RequestMapping(value = { "/chkalert" }, method = { RequestMethod.GET },
    // consumes = {
    // MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    // public Map<String, Object> chkalertGET(
    // @RequestHeader(name = "token") String token,
    // @ModelAttribute MeminterestEntity memInter,
    // @RequestParam(name = "mialert") long mialert) {
    // Map<String, Object> map = new HashMap<>();

    // try {
    // // 토큰 사용
    // String userid = jwtUtil.extractUsername(token);
    // System.out.println("userid =>" + userid);

    // // 회원 연결
    // MemberEntity memEntity = new MemberEntity();
    // memEntity.setUid(userid);
    // System.out.println("memberEntity =>" + memEntity);

    // // long mialert = memInter.getMialert();
    // int ret = memIntService1.chkalertinterest(userid, mialert);
    // if (ret == 1) {
    // map.put("reslut", "관심사 on");
    // map.put("status", 200);
    // } else {
    // map.put("reslut", "관심사 off");
    // map.put("status", 0);
    // }
    // } catch (Exception e) {
    // e.printStackTrace();
    // map.put("status", -1);
    // }
    // return map;
    // }

}
