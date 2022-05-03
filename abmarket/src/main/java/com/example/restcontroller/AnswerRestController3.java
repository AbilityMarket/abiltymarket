package com.example.restcontroller;

import java.util.HashMap;
import java.util.Map;

import com.example.entity.AnswerEntity;
import com.example.entity.InquireEntity;
import com.example.entity.MemberEntity;
import com.example.jwt.JwtUtil;
import com.example.repository.AnswerRepository3;
import com.example.repository.MemberRespository2;
import com.example.service.AnswerService3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
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
    @Autowired JwtUtil jwtUtil;

    @Autowired AnswerRepository3 anRepository3;

    @Autowired AnswerService3 anService3;

    @Autowired MemberRespository2 memRepository2;

    
    // 답변 등록 (관리자 토큰)
    // 127.0.0.1:9090/ROOT/api/answer/insertone
    @RequestMapping(value = {"/insertone"},
        method = {RequestMethod.POST},
        consumes = {MediaType.ALL_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public Map<String, Object> insertPOST(
        @RequestHeader(name = "token") String token,
        @RequestParam(name = "inqno") long inqno,
        @RequestBody AnswerEntity answerEntity) {

        Map<String, Object> map = new HashMap<>();

        try {
            //토큰 필요함(토큰 추출)
            String userid = jwtUtil.extractUsername(token);
            System.out.println("RequestMapping username : " + userid);
            
            //관리자
            MemberEntity mem = memRepository2.getById(userid);
            // System.out.println(mem);

            //토큰 = ADMIN
            if(mem.getUrole().equals("ADMIN")) {
                InquireEntity inq = new InquireEntity();
                inq.setInqno(inqno);
                System.out.println(inq.toString());

                answerEntity.setInquire(inq);
                System.out.println(answerEntity.toString());

                int ret = anService3.insertAnswer(answerEntity);
                if(ret == 1) {
                    map.put("result", "등록완료");
                    map.put("status", 200);
                }
                else {
                    map.put("result", "관리자X");
                    map.put("status", 0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // 답변 삭제
    // 127.0.0.1:9090/ROOT/api/answer/deleteone
    @RequestMapping(value = {"/deleteone"},
        method = {RequestMethod.DELETE},
        consumes = {MediaType.ALL_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public Map<String, Object> deleteOnePUT(
        @RequestHeader(name = "token") String token,
        @RequestParam(name = "anno") long anno) {

        Map<String, Object> map = new HashMap<>();
        
        try {
            //토큰 필요함(토큰 추출)
            String username = jwtUtil.extractUsername(token);
            System.out.println("RequestMapping username : " + username);

            MemberEntity mem = memRepository2.getById(username);

            if(mem.getUrole().equals("ADMIN")) {
                int ret = anService3.deleteOneAnswer(anno);
                if(ret == 1) {
                    map.put("result", "삭제완료!");
                    map.put("status", 200);
                }
            }
            else {
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
    @RequestMapping(value = {"/selectone"},
        method = {RequestMethod.GET},
        consumes = {MediaType.ALL_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public Map<String, Object> selectOneGET(
        @RequestHeader(name = "token") String token,
        @RequestParam(name = "anno") long anno) {

        Map<String, Object> map = new HashMap<>();
        
        try {
            //토큰 필요함(토큰 추출)
            String userid = jwtUtil.extractUsername(token);
            System.out.println("RequestMapping username : " + userid);

            MemberEntity mem = memRepository2.getById(userid);

            if(mem.getUrole().equals("ADMIN")) {
                AnswerEntity answerEntity = anService3.selectOneAnswer(anno);
                if(answerEntity != null) {
                    map.put("status", 200);
                    map.put("result", answerEntity);
                }
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

    // 답변 수정 (관리자 토큰)
    // 127.0.0.1:9090/ROOT/api/answer/updateone
    @RequestMapping(value = {"/updateone"},
        method = {RequestMethod.PUT},
        consumes = {MediaType.ALL_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public Map<String, Object> updateOnePUT(
        @RequestHeader(name = "token") String token,
        @RequestBody AnswerEntity answeren) {

        Map<String, Object> map = new HashMap<>();

        try {
            //토큰 필요함(토큰 추출)
            String userid = jwtUtil.extractUsername(token);
            System.out.println("RequestMapping username : " + userid);

            MemberEntity mem = memRepository2.getById(userid);

            if(mem.getUrole().equals("ADMIN")) {
                //답변 기존 데이터 불러오기
                AnswerEntity result = anService3.selectOneAnswer(answeren.getAnno());
                //System.out.println("기존==="+result.toString());
    
                //답변 수정 (내용)
                result.setAncontent(answeren.getAncontent());
                //System.out.println("새로운==="+result.getAncontent());
    
                //변경 후 저장
                int ret = anService3.updateOneAnswer(result);
                if(ret == 1) {
                    map.put("result", "수정완료!");
                    map.put("status", 200);
                }
            }
            else {
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
