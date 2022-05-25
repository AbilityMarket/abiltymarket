package com.example.restcontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.entity.AnswerEntity;
import com.example.entity.InquireEntity;
import com.example.entity.MemberEntity;
import com.example.jwt.JwtUtil;
import com.example.repository.InquireRepository3;
import com.example.repository.MemberRespository2;
import com.example.service.AnswerService3;
import com.example.service.InquireService1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/inquire")

public class InquireRestController3 {

    // 토큰
    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    InquireService1 inqService1;

    @Autowired
    InquireRepository3 inqRepository3;

    @Autowired
    AnswerService3 answerService3;

    @Autowired
    MemberRespository2 memRepository2;

    // 문의글 등록 (토큰 필요)
    // 프론트 작업 시 시퀀스는 제외(index 사용하기)
    // 127.0.0.1:9090/ROOT/api/inquire/insert
    @RequestMapping(value = { "/insert" }, method = { RequestMethod.POST }, consumes = {
            MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> insertPOST(
            @RequestHeader(name = "token") String token,
            @ModelAttribute InquireEntity inquireEntity) {

        Map<String, Object> map = new HashMap<>();

        try {
            // 토큰 필요함(토큰 추출)
            String userid = jwtUtil.extractUsername(token);
            System.out.println("RequestMapping username : " + userid);

            MemberEntity memberEntity = new MemberEntity();
            memberEntity.setUid(userid);
            System.out.println(memberEntity);

            inquireEntity.setMember(memberEntity);
            System.out.println(inquireEntity.toString());

            int ret = inqService1.insertOne(inquireEntity);
            if (ret == 1) {
                map.put("result", "등록완료!");
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

    // 문의글 삭제 (작성자와 동일)
    // 127.0.0.1:9090/ROOT/api/inquire/deleteone
    @RequestMapping(value = { "/deleteone" }, method = { RequestMethod.DELETE }, consumes = {
            MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> deleteOne(
            @RequestHeader(name = "token") String token,
            @RequestParam(name = "inqno") long inqno) {

        Map<String, Object> map = new HashMap<>();

        try {
            // 토큰 필요함(토큰 추출)
            String userid = jwtUtil.extractUsername(token);
            System.out.println("RequestMapping username : " + userid);

            // 게시판 글번호 추출
            InquireEntity inquireEntity = inqRepository3.getById(inqno);
            System.out.println(inquireEntity.getInqno());

            if (userid.equals(inquireEntity.getMember().getUid())) {
                int ret = inqService1.deleteOne(inqno);
                if (ret == 1) {
                    map.put("result", "삭제완료!");
                    map.put("status", 200);
                }
            } else {
                map.put("result", "작성자X!");
                map.put("status", 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // 문의글 전체 목록 조회, 페이지네이션&검색 (작성자와 동일)
    // 127.0.0.1:9090/ROOT/api/inquire/selectlist
    @RequestMapping(value = { "/selectlist" }, method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> selectListInqGET(
            @RequestHeader(name = "token") String token,
            // @ModelAttribute InquireEntity inquireentity,
            @RequestParam(value = "title", defaultValue = "") String text,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "select", defaultValue = "1") long select) {

        Map<String, Object> map = new HashMap<>();

        // 페이지 기본 값 0
        Pageable pageable = PageRequest.of(page - 1, 10);

        try {
            // 토큰 필요함(토큰 추출)
            String userid = jwtUtil.extractUsername(token);
            System.out.println("RequestMapping username : " + userid);

            // if (userid.equals(inquireentity.getMember().getUid())) {
            // System.out.println(inquireentity.getMember().getUid());

            List<InquireEntity> list = inqService1.selectListPageSearchInquireMember_uid(pageable, text, select,
                    userid);
            if (list != null) {
                long total = inqService1.countSearch(text);
                map.put("title", text);
                map.put("page", page);
                // 문의글 1, FAQ 2
                map.put("select", select);
                map.put("list", list);
                map.put("total", total);
                map.put("status", 200);
            }
            // } else {
            // map.put("result", "작성자X!");
            // map.put("status", 0);
            // }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // 문의글 1개 조회 (작성자와 동일), 해당 답변 가져오기
    // repository 한 것과 기존 List 총 2개 나옴
    // 127.0.0.1:9090/ROOT/api/inquire/selectone
    @RequestMapping(value = { "/selectone" }, method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> selectOneInqGET(
            @RequestHeader(name = "token") String token,
            @RequestParam(name = "inqno") long inqno) {

        Map<String, Object> map = new HashMap<>();

        try {
            // 토큰 필요함(토큰 추출)
            String userid = jwtUtil.extractUsername(token);
            System.out.println("RequestMapping username : " + userid);

            InquireEntity iEntity = inqRepository3.getById(inqno);
            MemberEntity mem = memRepository2.getById(userid);

            if (userid.equals(iEntity.getMember().getUid()) || mem.getUrole().equals("ADMIN")) {
                InquireEntity inquireentity = inqService1.selectOne(inqno);
                List<AnswerEntity> list = answerService3.selectAnswerList(inquireentity.getInqno());
                System.out.println(inquireentity.getInqno());
                if (inquireentity != null) {
                    map.put("inquireentity", inquireentity);
                    map.put("list", list);
                    map.put("status", 200);
                }
            } else {
                map.put("result", "작성자X");
                map.put("status", 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // 문의글 수정 (작성자와 동일)
    // 127.0.0.1:9090/ROOT/api/inquire/updateone
    @RequestMapping(value = { "/updateone" }, method = { RequestMethod.PUT }, consumes = {
            MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> updateOneInqPUT(
            @RequestHeader(name = "token") String token,
            @ModelAttribute InquireEntity inquireentity) {

        Map<String, Object> map = new HashMap<>();

        try {
            // 토큰 필요함(토큰 추출)
            String userid = jwtUtil.extractUsername(token);
            System.out.println("RequestMapping username : " + userid);

            InquireEntity inqentity = inqRepository3.getById(inquireentity.getInqno());
            System.out.println(inqentity.getInqno());

            if (userid.equals(inqentity.getMember().getUid())) {
                InquireEntity result = inqService1.selectOne(inqentity.getInqno());
                // System.out.println("기존==="+result);

                // 수정 (판매구매 구분, 완료여부 수정X)
                result.setInqtitle(inquireentity.getInqtitle());
                result.setInqcontent(inquireentity.getInqcontent());
                result.setInqselecttype(inquireentity.getInqselecttype());
                // System.out.println("새로운==="+result.toString());

                int ret = inqService1.updateOne(result);
                if (ret == 1) {
                    map.put("result", "수정완료!");
                    map.put("status", 200);
                }
            } else {
                map.put("result", "작성자X");
                map.put("status", 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", "완전에러");
            map.put("status", -1);
        }
        return map;
    }

    // FAQ 등록 (관리자)
    // 프론트 작업 시 시퀀스는 제외(index 사용하기)
    // 127.0.0.1:9090/ROOT/api/inquire/faq/insertone
    @RequestMapping(value = { "/faq/insertone" }, method = { RequestMethod.POST }, consumes = {
            MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> insertonePOST(
            @RequestHeader(name = "token") String token,
            @ModelAttribute InquireEntity inquire) {

        Map<String, Object> map = new HashMap<>();

        try {
            // 토큰 필요함(토큰 추출)
            String userid = jwtUtil.extractUsername(token);
            System.out.println("RequestMapping username : " + userid);

            MemberEntity memberEntity = new MemberEntity();
            memberEntity.setUid(userid);
            System.out.println(memberEntity);

            inquire.setMember(memberEntity);
            System.out.println(inquire.toString());

            // 관리자
            MemberEntity mem = memRepository2.getById(userid);
            if (mem.getUrole().equals("ADMIN")) {
                int ret = inqService1.insertOne(inquire);
                if (ret == 1) {
                    map.put("result", "등록완료");
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

    // FAQ 삭제(관리자)
    // 127.0.0.1:9090/ROOT/api/inquire/faq/deleteone
    @RequestMapping(value = { "/faq/deleteone" }, method = { RequestMethod.DELETE }, consumes = {
            MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> deleteOneDELETE(
            @RequestHeader(name = "token") String token,
            @RequestParam(name = "inqno") long inqno) {

        Map<String, Object> map = new HashMap<>();

        try {
            // 토큰 필요함(토큰 추출)
            String userid = jwtUtil.extractUsername(token);
            System.out.println("RequestMapping username : " + userid);

            // 관리자
            MemberEntity mem = memRepository2.getById(userid);
            System.out.println(mem.getUid());
            if (mem.getUrole().equals("ADMIN")) {
                int ret = inqService1.deleteOne(inqno);
                if (ret == 1) {
                    map.put("result", "삭제완료");
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

    // FAQ 전체 조회(토큰)
    // 127.0.0.1:9090/ROOT/api/inquire/faq/selectlist
    @RequestMapping(value = { "/faq/selectlist" }, method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> selectListFqaGET(
            @RequestHeader(name = "token") String token,
            @RequestParam(value = "title", defaultValue = "") String text,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "select", defaultValue = "2") long select) {

        Map<String, Object> map = new HashMap<>();

        // 페이지 기본 값 0
        Pageable pageable = PageRequest.of(page - 1, 10);

        try {
            // 토큰 필요함(토큰 추출)
            String userid = jwtUtil.extractUsername(token);
            System.out.println("RequestMapping username : " + userid);

            List<InquireEntity> list = inqService1.selectListPageSearchInquireMember_uid(pageable, text, select,
                    userid);
            if (list != null) {
                long total = inqService1.countSearch(text);
                map.put("title", text);
                map.put("page", page);
                // 문의글 1, FAQ 2
                map.put("select", select);
                map.put("list", list);
                map.put("total", total);
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

    // FAQ 1개 조회(토큰)
    // 127.0.0.1:9090/ROOT/api/inquire/faq/selectone
    @RequestMapping(value = { "/faq/selectone" }, method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> selectOneFaqGET(
            @RequestHeader(name = "token") String token,
            @RequestParam(name = "code") long code) {

        Map<String, Object> map = new HashMap<>();

        try {
            // 토큰 필요함(토큰 추출)
            String userid = jwtUtil.extractUsername(token);
            System.out.println("RequestMapping username : " + userid);

            InquireEntity inquireentity = inqService1.selectOne(code);

            if (inquireentity != null) {
                map.put("inquireentity", inquireentity);
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

    // FAQ 수정(관리자)
    // 127.0.0.1:9090/ROOT/api/inquire/faq/updateone
    @RequestMapping(value = { "/faq/updateone" }, method = { RequestMethod.PUT }, consumes = {
            MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> updateOneFaqPUT(
            @RequestHeader(name = "token") String token,
            @ModelAttribute InquireEntity inquireentity) {

        Map<String, Object> map = new HashMap<>();
        map.put("status", 100);

        try {
            // 토큰 필요함(토큰 추출)
            String userid = jwtUtil.extractUsername(token);
            System.out.println("RequestMapping username : " + userid);

            // 해당 글 번호 호출
            Long inqentity = inquireentity.getInqno();
            System.out.println(inqentity);

            // 관리자
            MemberEntity mem = memRepository2.getById(userid);
            System.out.println(mem.getUid());

            if (mem.getUrole().equals("ADMIN")) {
                InquireEntity result = inqService1.selectOne(inqentity);
                System.out.println("기존===" + result.getInqtitle());

                // 수정
                result.setInqtitle(inquireentity.getInqtitle());
                result.setInqcontent(inquireentity.getInqcontent());
                System.out.println("새로운===" + result.getInqtitle());

                int ret = inqService1.updateOne(result);
                if (ret == 1) {
                    map.put("result", "수정완료!");
                    map.put("status", 200);
                } else {
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

}
