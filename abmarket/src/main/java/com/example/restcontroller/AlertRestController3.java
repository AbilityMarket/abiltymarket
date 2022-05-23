package com.example.restcontroller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.example.entity.AlertEntity;
import com.example.jwt.JwtUtil;
import com.example.repository.AlertRepository3;
import com.example.service.AlertService3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
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

    @Autowired AlertRepository3 alRepository3;


    // 알림 1개 삭제
    // alread가 0 일 때 삭제를 해야 할까요오오오오(다시 확인)
    // 127.0.0.1:9090/ROOT/api/alert/aldeleteone
    @RequestMapping(value = {"/aldeleteone"},
        method = {RequestMethod.DELETE},
        consumes = {MediaType.ALL_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public Map<String, Object> alertDeleteOne(
        @RequestHeader(name = "token") String token,
        @RequestParam(name = "alno") Long alno) {

        Map<String, Object> map = new HashMap<>();
        try {
            String userid = jwtUtil.extractUsername(token);
            System.out.println(userid);

            int ret = alService3.deleteAlert(alno);
            if(ret == 1) {
                map.put("result", "삭제완료!");
                map.put("status", 200);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // 알림 1개 조회(상세) 후 1->0으로 수정하기
    // 메시지만 나오게 설정
    // 127.0.0.1:9090/ROOT/api/alert/alselectone
    @RequestMapping(value = {"/alselectone"},
        method = {RequestMethod.GET},
        consumes = {MediaType.ALL_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public Map<String, Object> alSelectOne(
        @RequestHeader(name = "token") String token,
        @RequestParam(name = "alno") Long alno) {

        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);

        try {
            String userid = jwtUtil.extractUsername(token);
            System.out.println(userid);
            
            AlertEntity alertEnt = alService3.selectOneAlert(alno);
            //System.out.println(alertEnt.getAlread());

            if(alertEnt != null) {
                map.put("status", 200);
                map.put("result", alertEnt);

                Long alreadChk = alertEnt.getAlread();
                //System.out.println(alreadChk);

                // 읽은 후 1->0으로 수정
                if(alreadChk == 1L) {
                    alertEnt.setAlread(0L);
                    System.out.println("알림 상세 조회 후 1L => " + alertEnt.getAlread());
                    // 알림 확인 일자 추가
                    LocalDateTime readNow = LocalDateTime.now();
                    alertEnt.setAlreaddate(readNow);
                    alRepository3.save(alertEnt);
                    map.put("status", 100);
                }
                else {
                    map.put("status", 150);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // 읽지 않은(1) 알림 개수 호출
    // 127.0.0.1:9090/ROOT/api/alert/alunreadcnt
    @RequestMapping(value = {"/alunreadcnt"},
        method = {RequestMethod.GET},
        consumes = {MediaType.ALL_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public Map<String, Object> altUnReadCnt(
        @RequestHeader(name = "token") String token) {

        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);

        try {
            String userid = jwtUtil.extractUsername(token);
            // System.out.println(userid);
            // alread 확인
            AlertEntity alert = new AlertEntity();
            Long alreadChk = alert.getAlread();
            if(alreadChk == 1L) {
                Long result = alService3.alertUnReadCount(alreadChk, userid);
                map.put("result", result);
                map.put("status", 200);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }
    
    // 알림 전체 목록 조회 불러오기(페이지) (검색X)
    // 읽기 여부 상관X
    // 메시지만 나오게 설정
    // 127.0.0.1:9090/ROOT/api/alert/alalllist
    @RequestMapping(value = {"/alalllist"},
        method = {RequestMethod.GET},
        consumes = {MediaType.ALL_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public Map<String, Object> alertAllList(
        @RequestHeader(name = "token") String token,
        @RequestParam(value = "page", defaultValue = "0") int page) {

        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        
        Pageable pageable = PageRequest.of(page-1, 7);
        
        try {
            String userid = jwtUtil.extractUsername(token);
            //System.out.println(userid);
            
            List<AlertEntity> list = alService3.selectAlertAllList(pageable, userid);
            if(list != null) {
                map.put("status", 200);
                map.put("list", list);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // 읽지 않은(1) 알림 목록 조회(페이지) (검색X)
    // 127.0.0.1:9090/ROOT/api/alert/alreadlist
    @RequestMapping(value = {"/alreadlist"},
        method = {RequestMethod.GET},
        consumes = {MediaType.ALL_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public Map<String, Object> alertReadList(
        @RequestHeader(name = "token") String token,
        @RequestParam(value = "page", defaultValue = "0") int page) {

        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);

        Pageable pageable = PageRequest.of(page-1, 7);
        
        try {
            String userid = jwtUtil.extractUsername(token);
            System.out.println(userid);

            AlertEntity alert = new AlertEntity();
            Long alreadChk = alert.getAlread();
            if(alreadChk == 1L) {
                List<AlertEntity> list = alService3.selectUnReadAlertList(pageable, userid, alreadChk);
                if(list != null) {
                    map.put("list", list);
                    map.put("status", 200);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // 알림 종류 확인(댓글, 대댓글, 후기 등 확인)
    // 127.0.0.1:9090/ROOT/api/alert/altypechk
    @RequestMapping(value = {"/altypechk"},
        method = {RequestMethod.GET},
        consumes = {MediaType.ALL_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public Map<String, Object> alTypeChk(
        @RequestParam(name = "alno") Long alno) {

        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);

        try {
            AlertEntity alertEnt = new AlertEntity();
            AlertEntity alert = alService3.alertTypeChk(alno, alertEnt.getAltype());
            // System.out.println(alert.getAltype());
            map.put("result", alert.getAltype());
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }


    public static Map<String, SseEmitter> sseEmitters = new ConcurrentHashMap<>();
    //public static Map<String, SseEmitter> sseEmitters = new HashMap<>();

    // 로그인한 회원과 실시간 알림 연결
    // 127.0.0.1:9090/ROOT/api/alert/sub
    @RequestMapping(value = {"/sub"}, 
        method = {RequestMethod.GET},
        consumes = {MediaType.ALL_VALUE}
    )
    public SseEmitter subscribe(@RequestParam String TOKEN) {
        // 토큰 추출
        String userid = jwtUtil.extractUsername(TOKEN);
        System.out.println("SSE token 확인==="+userid);
		
        // 현재 클라이언트를 위한 SseEmitter 생성
        //SseEmitter emitter = new SseEmitter(1000L);
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        
        try {
            // 연결
            System.out.println("여기까지 오나 확인11111111111111");
            emitter.send(SseEmitter.event().name("connect").data("연결완료"+LocalTime.now().toString()).reconnectTime(1000L));
            System.out.println(emitter.toString()); //SseEmitter@계속바뀜
            System.out.println("여기까지 오나 확인22222222222222");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("연결에러" + e); 
        }
        
        // user의 pk값을 key값으로 해서 SseEmitter를 저장
        sseEmitters.put(userid, emitter);

        emitter.onCompletion(() -> {
            sseEmitters.remove(userid);
            System.out.println("1111111");
        });
        
        emitter.onTimeout(() -> {
            sseEmitters.remove(userid);
            System.out.println("222222");
        });
        
        emitter.onError((e) -> {
            sseEmitters.remove(userid);
            System.out.println("333333");
        });

        return emitter;
    }

}


