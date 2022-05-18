package com.example.restcontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.entity.AlertEntity;
import com.example.entity.ChatEntity;
import com.example.entity.ChatroomEntity;
import com.example.entity.MemberEntity;
import com.example.entity.ReviewEntity;
import com.example.jwt.JwtUtil;
import com.example.repository.ChatRepository2;
import com.example.repository.ChatroomRepository2;
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

    @Autowired ChatRepository2 chatRepository2;
    @Autowired ChatroomRepository2 chatroomRepository2;

    // 후기 작성하기
    // crno에 후기는 1개만 쓸 수 있게 설정 해야 됨
    // 거래 완료 CHSTATE TDONE -> 후기 쓸 수 있게 설정
    // 127.0.0.1:9090/ROOT/api/review/insert
    @RequestMapping(value = "/insert", method = { RequestMethod.POST }, consumes = { MediaType.ALL_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> insertPost(
            @ModelAttribute ReviewEntity revEntity,
            @RequestParam(name="crno") Long crno,
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
                System.out.println(revEntity.getRevno()); //24

                // ChatEntity에 해당 리뷰 번호 넣기
                List<ChatEntity> chList = chatRepository2.findByChatroom_crno(crno);
                for(ChatEntity chatEnt : chList) {
                    //System.out.println(chatEnt.getChatroom().getCrno()); // 5
                    chatEnt.setReview(revEntity);  // 리뷰 엔티티
                    chatRepository2.save(chatEnt); // 저장
                }

                ChatroomEntity chroomEnt = chatroomRepository2.getById(crno);          
                try {
                    // 알림 DB 저장 호출
                    // 타입, url, 아이디 설정
                    AlertEntity alert = new AlertEntity();
                    alert.setAltype(2L);
                    // 해당 글 url
                    alert.setAlurl("/ROOT/api/board/selectone?bno=" + chroomEnt.getBoard().getBno());
                    //해당 회원 아이디 (게시판 작성자)
                    MemberEntity memEnt = new MemberEntity();
                    memEnt.setUid(chroomEnt.getBoard().getMember().getUid());
                    alert.setMember(memEnt);

                    alertServiceImpl3.insertAlert(alert);
                
                    // 여기에 알림 호출 (답변 단 해당 문의글 쓴 회원에게 알림 호출)
                    alertServiceImpl3.sendReviewAlert(chroomEnt, alert);

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
