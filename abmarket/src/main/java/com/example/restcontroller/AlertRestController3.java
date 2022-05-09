package com.example.restcontroller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.example.entity.AlertEntity;
import com.example.entity.MemberEntity;
import com.example.jwt.JwtUtil;
import com.example.service.AlertService3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping(value = "/api/alert")

// 알람(단방향) -> 게시판 댓글(대댓글), 문의답변, 찜, 후기, 신고, 등급
// 채팅알람(양방향)
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
            //5-찜(bolike), 6-신고(report)
            
            int ret = alService3.insertAlert(alertet);
            //System.out.println(ret);
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

    // 읽지 않은 알림 1 표시
    // 127.0.0.1:9090/ROOT/api/alert/alunreadcnt
    @RequestMapping(value = {"/alunreadcnt"},
        method = {RequestMethod.GET},
        consumes = {MediaType.ALL_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public Map<String, Object> altUnReadCntGET(
        @RequestHeader(name = "token") String token,
        @RequestParam(name = "alno") long alno) {

        Map<String, Object> map = new HashMap<>();
        try {
            String userid = jwtUtil.extractUsername(token);
            System.out.println(userid);
            //AlertService3.alertUnReadCount(Long alno) : Long
            Long unreadcnt = alService3.alertUnReadCount(alno);
            System.out.println(unreadcnt);
            if(unreadcnt != null) {
                map.put("result", "알림읽지않음");
                map.put("status", 200);
            }
            else {
                map.put("result", "알림읽음");
                map.put("status", 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // 알림 읽으면 1 표시 지우기
    // 127.0.0.1:9090/ROOT/api/alert/altreadup
    @RequestMapping(value = {"/alreadup"},
        method = {RequestMethod.PUT},
        consumes = {MediaType.ALL_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public Map<String, Object> alReadUpPUT(
        @RequestHeader(name = "token") String token) {

        Map<String, Object> map = new HashMap<>();

        try {
            String userid = jwtUtil.extractUsername(token);
            System.out.println(userid);
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // 알람 목록
    // 127.0.0.1:9090/ROOT/api/alert/alreadlist
    @RequestMapping(value = {"/alreadlist"},
        method = {RequestMethod.GET},
        consumes = {MediaType.ALL_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public Map<String, Object> alReadListGET(
        @RequestHeader(name = "token") String token,
        @RequestParam(value = "page", defaultValue = "0") int page) {

        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);

        Pageable pageable = PageRequest.of(page-1, 5);

        try {
            String userid = jwtUtil.extractUsername(token);
            System.out.println(userid);

            List<AlertEntity> list = alService3.selectAlertList(pageable);
            System.out.println(list);

            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    public static Map<String, SseEmitter> sseEmitters = new ConcurrentHashMap<>();

    // 127.0.0.1:9090/ROOT/api/alert/sub
    @GetMapping(value = "/sub", consumes = MediaType.ALL_VALUE)
    public SseEmitter subscribe(@RequestHeader(name = "token") String token) {
			
        // 토큰 추출
        String userid = jwtUtil.extractUsername(token);
        System.out.println(userid);
		
        // 현재 클라이언트를 위한 SseEmitter 생성
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
        try {
            // 연결!!
            sseEmitter.send(SseEmitter.event().name("connect"));
            System.out.println(sseEmitter.toString()); //SseEmitter@계속바뀜
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // user의 pk값을 key값으로 해서 SseEmitter를 저장
        sseEmitters.put(userid, sseEmitter);

        sseEmitter.onCompletion(() -> sseEmitters.remove(userid));
        sseEmitter.onTimeout(() -> sseEmitters.remove(userid));
        sseEmitter.onError((e) -> sseEmitters.remove(userid));

        return sseEmitter;
    }
}


