package com.example.restcontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.entity.InterestEntity;
import com.example.entity.MemIntAndBodAndBodInt;
import com.example.entity.MemberEntity;
import com.example.entity.MeminterestEntity;
import com.example.jwt.JwtUtil;
import com.example.repository.MemIntAndBodAndBodIntRepository3;
import com.example.repository.MemInterestRepository1;
import com.example.service.MemInterestService1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
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

    @Autowired
    MemIntAndBodAndBodIntRepository3 memIntAndBodAndBodIntRepository3;


    // 관심사 알람설정 유무 확인(0L or 1L)
    // 127.0.0.1:9090/ROOT/api/meminterest/chkalert
    @RequestMapping(value = { "/chkalert" }, method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> chkalertGET(
            @RequestHeader(name = "token") String token,
            @ModelAttribute MeminterestEntity memInter) {
        Map<String, Object> map = new HashMap<>();

        try {
            // 토큰 사용
            String userid = jwtUtil.extractUsername(token);
            System.out.println("userid : " + userid);

            // 유무 확인
            int ret = memIntService1.chkalertinterest(userid, 1L);
            System.out.println("ret : " + ret);

            if (ret > 0) {
                map.put("reslut", "관심사 있다");
                map.put("status", 200);
            } else {
                map.put("reslut", "관심사 없다");
                map.put("status", 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // 관심사 알람설정 등록(0 -> 1, 1-> 0)
    // 127.0.0.1:9090/ROOT/api/meminterest/alertchange
    // micode, mialert(0 or 1) 포스트맨에 두 가지 기입
    @RequestMapping(value = { "/alertchange" }, method = { RequestMethod.POST }, consumes = {
            MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> insertalerttPOST(
            @RequestHeader(name = "token") String token,
            @ModelAttribute MeminterestEntity meminterest) {
        Map<String, Object> map = new HashMap<>();

        try {
            // 토큰 사용
            String userid = jwtUtil.extractUsername(token);
            System.out.println("userid : " + userid);

            MeminterestEntity meminterest1 = memIntRepository1.findByMicodeAndMember_uid(meminterest.getMicode(),
                    userid);

            // 알림 설정이 되어 있을경우 설정을 해제한다(1->0)
            if (meminterest.getMialert() == 1L) {
                meminterest1.setMialert(0L);
                System.out.println("1일때 : " + meminterest);

            } // 알림 설정 확인 후 알림 설정이 안 되어 있으면 설정을 해준다.(0->1)
            else if (meminterest.getMialert() == 0L) {
                meminterest1.setMialert(1L);
                System.out.println("0일때 : " + meminterest);
            }
            int ret = memIntService1.updatealert(meminterest1);
            if (ret == 1) {
                map.put("result", "알람 설정 완료");
                map.put("status", 200);
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


    // 관심사별 회원 조회
    // 127.0.0.1:9090/ROOT/api/meminterest/intlistchk
    @RequestMapping(value = {"/intlistchk"},
        method = {RequestMethod.GET}, 
        consumes = {MediaType.ALL_VALUE}, 
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public Map<String, Object> intListChk(
        @RequestParam(name = "incode") List<Long> incode) {

        Map<String, Object> map = new HashMap<>();
        try {
            List<MeminterestEntity> memIncode = memIntService1.selectListInt(incode);
            if(memIncode != null) {
                map.put("status", 200);
                map.put("list", memIncode);
            }
            else {
                map.put("status", 0);
                map.put("list", memIncode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }


    // 해당 회원 관심사 조회
    // 127.0.0.1:9090/ROOT/api/meminterest/memintchk
    @RequestMapping(value = {"/memintchk"},
        method = {RequestMethod.GET}, 
        consumes = {MediaType.ALL_VALUE}, 
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public Map<String, Object> memIntChk(
        @RequestHeader(name = "token") String token) {

        Map<String, Object> map = new HashMap<>();
        try {
            //토큰 필요함(토큰 추출)
            String userid = jwtUtil.extractUsername(token);
            System.out.println("RequestMapping username : " + userid);

            List<MeminterestEntity> memUserid = memIntService1.selectListMemInt(userid);
            if(memUserid != null) {
                map.put("status", 200);
                map.put("list", memUserid);
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


    // 회원별 관심사 중 해당되는 게시판 구매 판매 조회 (뷰 생성)
    // 127.0.0.1:9090/ROOT/api/meminterest/memintchkbrole
    @RequestMapping(value = {"/memintchkbrole"},
        method = {RequestMethod.GET}, 
        consumes = {MediaType.ALL_VALUE}, 
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public Map<String, Object> memIntChkBrole(
        @RequestHeader(name = "token") String token) {

        Map<String, Object> map = new HashMap<>();
        try {
            //토큰 필요함(토큰 추출)
            String userid = jwtUtil.extractUsername(token);
            System.out.println("RequestMapping username : " + userid);
            
            List<MemIntAndBodAndBodInt> list = memIntService1.chkBoardBrole(userid);
            if(list != null) {
                map.put("status", 200);
                map.put("list", list);
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


}
