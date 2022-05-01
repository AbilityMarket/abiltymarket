package com.example.restcontroller;

import java.util.HashMap;
import java.util.Map;

import com.example.entity.AnswerEntity;
import com.example.entity.InquireEntity;
import com.example.jwt.JwtUtil;
import com.example.repository.AnswerRepository3;
import com.example.service.AnswerService3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/answer")

public class AnswerRestController3 {

    // 문의 답변은 게시판(댓글) 형태로 설정(관리자가 입력)
    
    // 토큰
    @Autowired JwtUtil jwtUtil;

    @Autowired AnswerRepository3 anRepository3;

    @Autowired AnswerService3 anService3;

    
    // 답변 등록 (관리자 토큰)
    // 127.0.0.1:9090/ROOT/api/answer/insert
    @RequestMapping(value = {"/insert"},
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

            InquireEntity inq = new InquireEntity();
            inq.setInqno(inqno);
            System.out.println(inq.toString());

            answerEntity.setInquire(inq);
            System.out.println(answerEntity.toString());

            int ret = anService3.insertAnswer(answerEntity);
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

    // 답변 수정 (관리자 토큰)
    // 127.0.0.1:9090/ROOT/api/answer/updateone
    @RequestMapping(value = {"/updateone"},
        method = {RequestMethod.PUT},
        consumes = {MediaType.ALL_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public Map<String, Object> updateOnePUT(
        @RequestHeader(name = "token") String token,
        @RequestParam(name = "anno") long anno,
        @ModelAttribute AnswerEntity answeren) {

        Map<String, Object> map = new HashMap<>();

        try {
            //토큰 필요함(토큰 추출)
            String userid = jwtUtil.extractUsername(token);
            System.out.println("RequestMapping username : " + userid);

            //답변 글번호 추출
            AnswerEntity answer = anRepository3.getById(anno);
            System.out.println(answer);

            //답변 기존 데이터 불러오기
            AnswerEntity result = anService3.selectOneAnswer(answeren.getAnno());

            //답변 수정 (내용)
            result.setAncontent(answeren.getAncontent());

            //변경 후 저장
            int ret = anService3.updateOneAnswer(result);
            if(ret == 1) {
                map.put("result", "수정완료!");
                map.put("status", 200);
            }
            else {
                map.put("result", "수정실패!");
                map.put("status", 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // 답변 삭제 (관리자 토큰)



}
