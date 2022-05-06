package com.example.restcontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.entity.BoardEntity;
import com.example.entity.BolikeEntity;
import com.example.entity.MemberEntity;
import com.example.jwt.JwtUtil;
import com.example.repository.BolikeRepository3;
import com.example.service.BolikeService3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/bolike")
public class BolikeRestController3 {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    BolikeService3 bolikeService3;

    @Autowired
    BolikeRepository3 bolikeRepository3;

    // 찜 등록(토큰 필요)
    // 찜 유무 먼저 확인
    // 127.0.0.1:9090/ROOT/api/bolike/like
    @RequestMapping(value = {"/like"},
        method = {RequestMethod.POST},
        consumes = {MediaType.ALL_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public Map<String, Object> likePOST(
        @RequestHeader(name = "token") String token,
        @RequestBody BolikeEntity bolike) {
        
        Map<String, Object> map = new HashMap<>();

        try {
            //토큰 필요함(토큰 추출)
            String userid = jwtUtil.extractUsername(token);
            System.out.println("RequestMapping username : " + userid);

            Long bno = bolike.getBoard().getBno();

            // db 유무 확인
            int bEntity = bolikeService3.chkBolike(userid, bno);
            System.out.println("bEntity==="+bEntity);
            // db에 있으면 1, 없으면 0 (찜등록)
            //{"board" : {"bno":"2"}}

            if(bEntity == 0) {
                MemberEntity memberEntity = new MemberEntity();
                memberEntity.setUid(userid);
                bolike.setMember(memberEntity);
                //System.out.println(memberEntity);

                BoardEntity boardEntity = new BoardEntity();
                boardEntity.setBno(bno);
                bolike.setBoard(boardEntity);
                //System.out.println(boardEntity);
                        
                int ret = bolikeService3.insertBolike(bolike);
                if(ret == 1) {
                    map.put("status", 200);
                    map.put("result", "찜등록!");
                }
            }
            else if(bEntity == 1) {
                map.put("status", 0);
                map.put("status", "이미찜");
                // 여기에서 찜취소 (삭제)가 되도록 설정
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // 찜 취소(삭제) (토큰 = 찜 등록한 사람)
    // 찜 유무 먼저 확인
    // 127.0.0.1:9090/ROOT/api/bolike/unlike
    @RequestMapping(value = {"/unlike"},
        method = {RequestMethod.DELETE},
        consumes = {MediaType.ALL_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public Map<String, Object> unlikeDELETE(
        @RequestHeader(name = "token") String token,
        @RequestBody BolikeEntity bolike) {

        Map<String, Object> map = new HashMap<>();

        try {
            //토큰 필요함(토큰 추출)
            String userid = jwtUtil.extractUsername(token);
            System.out.println("RequestMapping username : " + userid);

            Long bno = bolike.getBoard().getBno();

            // db 유무 확인
            int bEntity = bolikeService3.chkBolike(userid, bno);
            System.out.println("bEntity==="+bEntity);
            // db에 있으면 1(찜취소), 없으면 0
            //{"member" : {"uid" : "gg"},"board" : {"bno":"1"}}

            //토큰 = 찜 등록한 사람
            if(bolike.getMember().getUid().equals(userid)) {
                if(bEntity == 1) {
                    int ret = bolikeService3.deleteBolike(userid, bno);
                    if(ret == 1) {
                        map.put("result", "찜취소");
                        map.put("status", 200);
                    }
                }
                else if(bEntity == 0) {
                    map.put("status", 0);
                    map.put("result", "이미취소");
                    // 여기에서 찜등록 되도록 설정
                }
            }
            else {
                map.put("result", "찜등록자X");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // 찜1개 조회 (확인용)
    // 127.0.0.1:9090/ROOT/api/bolike/likeone
    @RequestMapping(value = {"/likeone"},
        method = {RequestMethod.GET},
        consumes = {MediaType.ALL_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public Map<String, Object> likeOneGET(
        @RequestParam(name = "bolno") Long bolno) {
        Map<String, Object> map = new HashMap<>();
        try {
            BolikeEntity bEntity = bolikeService3.bolikeOne(bolno);
            map.put("bEntity", bEntity);
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // 해당 게시물 찜 갯수 (토큰필요X)
    // 글번호 필요
    // 127.0.0.1:9090/ROOT/api/bolike/countlike
    @RequestMapping(value = {"/countlike"},
        method = {RequestMethod.GET},
        consumes = {MediaType.ALL_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public Map<String, Object> countLikeGET(
        @RequestParam(name = "bno") Long bno) {

        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);

        try {
            Long result = bolikeService3.countBolike(bno);
            //System.out.println(result);
            map.put("result", result);
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }
    
    // 찜 전체 목록 조회 (회원 본인이 찜한 목록), 페이지네이션
    // 아이디 필요
    // 127.0.0.1:9090/ROOT/api/bolike/ilikelist
    @RequestMapping(value = {"/ilikelist"},
        method = {RequestMethod.GET},
        consumes = {MediaType.ALL_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public Map<String, Object> iLikeListGET(
        @RequestHeader(name = "token") String token,
        @RequestParam(value = "page", defaultValue = "0") int page) {

        Map<String, Object> map = new HashMap<>();
        
        Pageable pageable = PageRequest.of(page-1, 7);
        
        try {
            //토큰 필요함(토큰 추출)
            String userid = jwtUtil.extractUsername(token);
            System.out.println("RequestMapping username : " + userid);
            
            //Long bno = bolike.getBoard().getBno();
            
            List<BolikeEntity> list = bolikeService3.selectlistBolike(pageable, userid);
            if(list != null) {
                map.put("status", 200);
                map.put("list", list);
                map.put("page", page);
            }
            else {
                map.put("status", 0);
                map.put("result", "작성자X");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }
    
}
