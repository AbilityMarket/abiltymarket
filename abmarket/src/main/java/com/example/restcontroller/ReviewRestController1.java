package com.example.restcontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.entity.AlertEntity;
import com.example.entity.MemberEntity;
import com.example.entity.ReviewEntity;
import com.example.entity.Reviewview;
import com.example.jwt.JwtUtil;
import com.example.service.AlertServiceImpl3;
import com.example.service.ReviewService1;

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
@RequestMapping(value = "api/review")
public class ReviewRestController1 {

    @Autowired
    ReviewService1 revService1;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired AlertServiceImpl3 alertServiceImpl3;

    // 후기 작성하기
    // 127.0.0.1:9090/ROOT/api/review/insert
    @RequestMapping(value = "/insert", method = { RequestMethod.POST }, consumes = { MediaType.ALL_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> insertPost(
            @ModelAttribute ReviewEntity revEntity,
            @RequestHeader(name = "token") String token) {

        Map<String, Object> map = new HashMap<>();

        try {
            String userid = jwtUtil.extractUsername(token);
            System.out.println("userid =>" + userid);

            MemberEntity mEntity = new MemberEntity();
            mEntity.setUid(userid);

            int ret = revService1.insertReview(revEntity);
            if (ret == 1) {
                map.put("status", 200);
                try {
                    // 알림 DB 저장 호출
                    // 타입, url, 아이디 설정
                    AlertEntity alert = new AlertEntity();
                    alert.setAltype(2L);
                    // 해당 문의글 url
                    alert.setAlurl("/ROOT/api/review/insert" + revEntity.getRevno());
                    //해당 회원 아이디 (*** 채팅 구현 후 다시 설정해야 됨 ***)
                    //어느 엔티티에서 회원을 호출할지 정해야 됨
                    Reviewview reviewview = new Reviewview();
                    String rewUid = reviewview.getMemberUid();
                    MemberEntity memEnt = new MemberEntity();
                    memEnt.setUid(rewUid);
                    alert.setMember(memEnt);
                
                    // 여기에 알림 호출 (답변 단 해당 문의글 쓴 회원에게 알림 호출)
                    alertServiceImpl3.sendReviewAlert(reviewview, alert);
                    
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("답변호출에러===>"+e);
                    map.put("status", 100);
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

    // 후기 조회하기
    // 127.0.0.1:9090/ROOT/api/review/select?revno=1
    @RequestMapping(value = "/select", method = { RequestMethod.GET }, consumes = { MediaType.ALL_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> selectGET(
            @RequestParam(name = "revno") long revno) {

        Map<String, Object> map = new HashMap<>();

        try {
            MemberEntity mEntity = new MemberEntity();
            mEntity.setUid(mEntity.getUid());

            ReviewEntity retReview = revService1.selectReview(revno);
            if (retReview != null) {
                map.put("status", 200);
                map.put("result", retReview.getRevno());
            } else {
                map.put("status", 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // 후기 수정하기
    // 127.0.0.1:9090/ROOT/api/review/update?revno=1
    @RequestMapping(value = "/update", method = { RequestMethod.PUT }, consumes = { MediaType.ALL_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> updatePost(
            @ModelAttribute ReviewEntity revEntity,
            @RequestHeader(name = "token") String token) {

        Map<String, Object> map = new HashMap<>();

        try {
            String userid = jwtUtil.extractUsername(token);
            System.out.println("userid =>" + userid);

            MemberEntity mEntity = new MemberEntity();
            mEntity.setUid(userid);

            ReviewEntity revEntity1 = revService1.selectReview(revEntity.getRevno());
            revEntity1.setRevcontent(revEntity.getRevcontent());
            revEntity1.setRevscore(revEntity.getRevscore());

            int ret = revService1.updateReview(revEntity1);
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

    // 후기 삭제하기
    // 127.0.0.1:9090/ROOT/api/review/delete?revno=1
    @RequestMapping(value = "/delete", method = { RequestMethod.DELETE }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> deletePost(
            @RequestHeader(name = "token") String token,
            @RequestParam(name = "revno") long revno) {

        Map<String, Object> map = new HashMap<>();

        try {
            String userid = jwtUtil.extractUsername(token);
            System.out.println("userid =>" + userid);

            MemberEntity mEntity = new MemberEntity();
            mEntity.setUid(userid);

            int ret = revService1.deleteReview(revno);
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

    // 후기 목록 조회 (페이지, 내용검색)
    // 127.0.0.1:9090/ROOT/api/review/selectList
    @RequestMapping(value = { "/selectList" }, method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> selectListGET(
            @RequestParam(value = "revcontent", defaultValue = "") String revcontent,
            @RequestParam(value = "page", defaultValue = "0") int page
    // ,@RequestParam(name = "bno") Long bno
    ) {

        Map<String, Object> map = new HashMap<>();

        Pageable pageable = PageRequest.of(page - 1, 10);

        try {
            // 후기 총 갯수
            // Long total = revService1.countReview(bno);

            List<ReviewEntity> list = revService1.selectListReview(pageable, revcontent);
            if (list != null) {
                // map.put("total", total);
                map.put("page", page);
                map.put("list", list);
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
