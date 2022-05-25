package com.example.restcontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.entity.AlertEntity;
import com.example.entity.AnswerEntity;
import com.example.entity.InquireEntity;
import com.example.entity.MemberEntity;
import com.example.jwt.JwtUtil;
import com.example.repository.AnswerRepository3;
import com.example.repository.InquireRepository3;
import com.example.repository.MemberRespository2;
import com.example.service.AlertService3;
import com.example.service.AlertServiceImpl3;
import com.example.service.AnswerService3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/answer")

public class AnswerRestController3 {

    // 문의 답변은 댓글 형태로 설정(관리자가 입력)

    // 토큰
    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    AnswerRepository3 anRepository3;

    @Autowired
    AnswerService3 anService3;

    @Autowired
    MemberRespository2 memRepository2;

    @Autowired
    AlertServiceImpl3 alertServiceImpl3;

    @Autowired
    AlertService3 alService3;

    @Autowired
    InquireRepository3 inqRepository3;

    // 답변 등록 (관리자 토큰)
    // 127.0.0.1:9090/ROOT/api/answer/insertone
    @RequestMapping(value = { "/insertone" }, method = { RequestMethod.POST }, consumes = {
            MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> insertAnswerOne(
            @RequestHeader(name = "token") String token,
            @RequestParam(name = "inqno") long inqno,
            @ModelAttribute AnswerEntity answerEntity) {

        Map<String, Object> map = new HashMap<>();

        try {
            // 토큰 필요함(토큰 추출)
            String userid = jwtUtil.extractUsername(token);
            System.out.println("RequestMapping username : " + userid);

            // 관리자
            MemberEntity mem = memRepository2.getById(userid);
            mem.setUid(userid);
            answerEntity.setMember(mem);

            // 토큰 = ADMIN
            if (mem.getUrole().equals("ADMIN")) {
                InquireEntity inqEnt = inqRepository3.getById(inqno);
                System.out.println(inqEnt.getInqno());

                answerEntity.setInquire(inqEnt);

                int ret = anService3.insertAnswer(answerEntity);
                if (ret == 1) {
                    // 답변완료여부 inqtype (1->완료X, 0->완료) 수정
                    Long inqTypeChk = inqEnt.getInqtype();
                    // System.out.println(inqTypeChk);
                    if (inqTypeChk == 1L) {
                        inqEnt.setInqtype(0L);
                        System.out.println("문의 답변 후 1L => " + inqEnt.getInqtype());
                        inqRepository3.save(inqEnt);
                        map.put("result", "답변완료");
                        map.put("status", 200);
                    } else {
                        map.put("status", 100);
                    }
                    try {
                        // 알림 DB 저장 호출
                        // 타입, url, 아이디 설정
                        AlertEntity alert = new AlertEntity();
                        alert.setAltype(1L);
                        // 해당 문의글 url
                        alert.setAlurl("/ROOT/api/inquire/selectone?inqno=" + inqEnt.getInqno());
                        // 해당 회원 아이디
                        Long iLong = inqEnt.getInqno();
                        // System.out.println(iLong);
                        InquireEntity iEntity = inqRepository3.getById(iLong);
                        // System.out.println(iEntity.getMember().getUid());
                        String inqUid = iEntity.getMember().getUid();
                        MemberEntity memEnt = new MemberEntity();
                        memEnt.setUid(inqUid); // String uid
                        alert.setMember(memEnt); // 멤버 엔티티

                        alertServiceImpl3.insertAlert(alert);

                        // 답변 단 해당 문의글 쓴 회원에게 알림 호출
                        alertServiceImpl3.sendAnswerAlert(inqEnt, alert);

                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("답변호출에러===>" + e);
                    }
                }
            } else {
                map.put("result", "관리자X");
                map.put("status", 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error==>" + e);
            map.put("status", -1);
        }
        return map;
    }

    // 답변 삭제
    // 127.0.0.1:9090/ROOT/api/answer/deleteone
    @RequestMapping(value = { "/deleteone" }, method = { RequestMethod.DELETE }, consumes = {
            MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> deleteOne(
            @RequestHeader(name = "token") String token,
            @RequestParam(name = "anno") long anno) {

        Map<String, Object> map = new HashMap<>();

        try {
            // 토큰 필요함(토큰 추출)
            String username = jwtUtil.extractUsername(token);
            System.out.println("RequestMapping username : " + username);

            MemberEntity mem = memRepository2.getById(username);

            if (mem.getUrole().equals("ADMIN")) {
                int ret = anService3.deleteOneAnswer(anno);
                if (ret == 1) {
                    map.put("result", "삭제완료!");
                    map.put("status", 200);
                }
            } else {
                map.put("result", "관리자X");
                map.put("status", 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // 답변 1개 조회
    // 127.0.0.1:9090/ROOT/api/answer/selectone
    @RequestMapping(value = { "/selectone" }, method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> selectOne(
            @RequestHeader(name = "token") String token,
            @RequestParam(name = "anno") long anno,
            @RequestParam(name = "inqno") long inqno) {

        Map<String, Object> map = new HashMap<>();

        try {
            // 토큰 필요함(토큰 추출)
            String userid = jwtUtil.extractUsername(token);
            System.out.println("RequestMapping username : " + userid);

            MemberEntity mem = memRepository2.getById(userid);

            InquireEntity inquire = new InquireEntity();
            inquire.setInqno(inqno);

            if (mem.getUrole().equals("ADMIN")) {
                List<AnswerEntity> list = anRepository3.findByInquire_inqnoOrderByAnnoDesc(anno);
                if (list != null) {
                    map.put("status", 200);
                    map.put("result", list);
                }
            } else {
                map.put("status", 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // 답변 수정 (관리자 토큰)
    // 127.0.0.1:9090/ROOT/api/answer/updateone
    @RequestMapping(value = { "/updateone" }, method = { RequestMethod.PUT }, consumes = {
            MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> updateOne(
            @RequestHeader(name = "token") String token,
            @ModelAttribute AnswerEntity answeren) {

        Map<String, Object> map = new HashMap<>();

        try {
            // 토큰 필요함(토큰 추출)
            String userid = jwtUtil.extractUsername(token);
            System.out.println("RequestMapping username : " + userid);

            MemberEntity mem = memRepository2.getById(userid);

            if (mem.getUrole().equals("ADMIN")) {
                // 답변 기존 데이터 불러오기
                AnswerEntity result = anService3.selectOneAnswer(answeren.getAnno());
                // System.out.println("기존==="+result.toString());

                // 답변 수정 (내용)
                result.setAncontent(answeren.getAncontent());
                // System.out.println("새로운==="+result.getAncontent());

                // 변경 후 저장
                int ret = anService3.updateOneAnswer(result);
                if (ret == 1) {
                    map.put("result", "수정완료!");
                    map.put("status", 200);
                }
            } else {
                map.put("result", "관리자X");
                map.put("status", 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

}
