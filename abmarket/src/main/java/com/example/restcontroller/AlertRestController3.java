package com.example.restcontroller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.example.entity.AlertEntity;
import com.example.jwt.JwtUtil;
import com.example.service.AlertService3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;


@RestController
@RequestMapping(value = "/api/alert")
public class AlertRestController3 {

    // 토큰
    @Autowired JwtUtil jwtUtil;

    @Autowired AlertService3 alService3;

    // 읽지 않은 알림 1 표시 (화면)
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

    // 알람 목록 (화면)
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

    // 로그인한 회원과 실시간 알림 연결
    // 127.0.0.1:9090/ROOT/api/alert/sub
    @GetMapping(value = {"/sub"}, consumes = MediaType.ALL_VALUE)
    public SseEmitter subscribe(@RequestParam String TOKEN) {
    	
        // 토큰 추출
        String userid = jwtUtil.extractUsername(TOKEN);
        System.out.println("SSE token 확인==="+userid);
		
        // 현재 클라이언트를 위한 SseEmitter 생성
        SseEmitter sseEmitter = new SseEmitter();
        try {
            // 연결
            sseEmitter.send(SseEmitter.event().id(userid).name("connect").data("연결완료"));
            //System.out.println(sseEmitter.toString()); //SseEmitter@계속바뀜
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


