package com.example.restcontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.entity.AlertEntity;
import com.example.entity.BoardEntity;
import com.example.entity.CommEntity;
import com.example.entity.MemberEntity;
import com.example.entity.RecommentEntity;
import com.example.jwt.JwtUtil;
import com.example.repository.BoardRepository1;
import com.example.repository.CommRepository2;
import com.example.service.AlertServiceImpl3;
import com.example.service.CommService2;

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
@RequestMapping(value = "/api/comm")
public class CommRestController2 {

    @Autowired
    CommService2 cService2;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    BoardRepository1 bRepository1;

    @Autowired AlertServiceImpl3 alertServiceImpl3;

    @Autowired CommRepository2 commRepository2;

    // 댓글 쓰기
    // 127.0.0.1:9090/ROOT/api/comm/insert
    @RequestMapping(value = "/insert", method = { RequestMethod.POST }, consumes = { MediaType.ALL_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> insertPost(
            @RequestParam(name = "bno") Long bno,
            @ModelAttribute CommEntity comm,
            @RequestHeader(name = "token") String token) {

        Map<String, Object> map = new HashMap<>();

        try {
            String userid = jwtUtil.extractUsername(token);
            System.out.println("userid =>" + userid);

            MemberEntity mEntity = new MemberEntity();
            mEntity.setUid(userid);

            BoardEntity board = new BoardEntity();
            board.setBno(bno);

            // comm에 지금 로그인한 사람 넣기
            comm.setMember(mEntity);
            comm.setBoard(board);

            int ret = cService2.insertComm(comm);

            if (ret == 1) {
                map.put("status", 200);
                try {
                    // 알림 DB 저장 호출
                    AlertEntity alert = new AlertEntity();
                    alert.setAltype(3L);
                    // 해당 판매자 글 url
                    alert.setAlurl("/ROOT/api/board/selectone?bno=" + board.getBno());
                    Long bLong = board.getBno();
                    //System.out.println(iLong);
                    BoardEntity bEntity = bRepository1.getById(bLong);
                    //System.out.println(bEntity.getMember().getUid());
                    String bodUid = bEntity.getMember().getUid();
                    MemberEntity mement = new MemberEntity();
                    mement.setUid(bodUid);
                    alert.setMember(mement);

                    alertServiceImpl3.insertAlert(alert);

                    // 여기에 알림 호출
                    alertServiceImpl3.sendCommAlert(board, alert);
                    
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

    // 댓글 삭제
    // 127.0.0.1:9090/ROOT/api/comm/delete
    @RequestMapping(value = "/delete", method = { RequestMethod.DELETE }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> deletePost(
            @RequestParam(name = "cono") Long cono,
            @RequestHeader(name = "token") String token) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);

        try {
            String uid = jwtUtil.extractUsername(token);

            int ret = cService2.deleteComm(uid, cono);

            if (ret == 1) {
                map.put("status", 200);
                map.put("msg", "삭제 완료");
            }
            if (ret == 0) {
                map.put("msg", "본인 댓글 아님");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // 게시글 수정
    // 127.0.0.1:9090/ROOT/api/comm/update
    @RequestMapping(value = "/update", method = { RequestMethod.PUT }, consumes = { MediaType.ALL_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> updatePut(
            @RequestHeader(name = "token") String token,
            @ModelAttribute CommEntity comm) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);

        try {
            String uid = jwtUtil.extractUsername(token);

            int ret = cService2.updateComm(uid, comm);
            if (ret == 1) {
                map.put("status", 200);
            }

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // 댓글 목록 조회
    @RequestMapping(value = { "/selectlist" }, method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> SelectListGET(
            @RequestParam(value = "bno") Long bno,
            @RequestHeader(value = "token", required = false) String token,
            @RequestParam(value = "page", defaultValue = "1") int page) {

        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        Pageable pageable = PageRequest.of(page - 1, 10);

        try {
            // 글 작성자 아이디 확인
            BoardEntity board = bRepository1.findById(bno).orElse(null);
            String boardWriter = board.getMember().getUid();
            System.out.println(boardWriter);
            // 현재 로그인한 사람이 있음.

            if (token != null) {
                String uid = jwtUtil.extractUsername(token);
                System.out.println(uid);

                List<CommEntity> list = cService2.selectListComm(pageable, bno);

                // list에서 비밀댓글 여부를 선택.
                // 지금 로그인한 사람이 댓글의 작성자이거나
                // 보드의 작성자일 경우만 읽을 수 있다.

                if (list != null) {
                    for (CommEntity comm : list) {
                        // 비공개여부일 경우
                        if (comm.getCoopen() == 2L) {
                            // 댓글 작성자나 글쓴이가 아닌 경우 확인 불가
                            if (!(uid.equals(comm.getMember().getUid())) &&
                                    !(uid.equals(boardWriter))) {
                                // 프론트에서 coopen이 4일 경우 세팅하기
                                // '비밀댓글입니다.'
                                comm.setCoopen(4L);
                            } else {
                                // 비공개 댓글이지만, 확인할 수 있는 권한
                                comm.setCoopen(3L);
                            }
                        }
                    }
                    map.put("status", 200);
                    map.put("list", list);
                }
            }

            // 로그인을 안 햇을 경우
            else {
                List<CommEntity> list = cService2.selectListComm(pageable, bno);
                if (list != null) {
                    // 비공개경우 4L로 바꾸기
                    for (CommEntity comm : list) {
                        if (comm.getCoopen() == 2L) {
                            comm.setCoopen(4L);
                        }

                    }
                    map.put("status", 200);
                    map.put("list", list);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // 자기가 쓴 글 표시하기
    // 127.0.0.1:9090/ROOT/api/comm/checkMine?cono=1
    @RequestMapping(value = { "/checkMine" }, method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> checkMineGET(
            @RequestParam(name = "cono") Long cono,
            @RequestHeader(name = "token", required = false) String token) {

        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);

        try {
            if (token != null) {
                String uid = jwtUtil.extractUsername(token);
                int ret = cService2.checkMine(cono, uid);
                if (ret == 1) {
                    map.put("status", 200);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // 대댓글 쓰기
    // 127.0.0.1:9090/ROOT/api/comm/insertRecomment?cono=
    @RequestMapping(value = "/insertRecomment", method = { RequestMethod.POST }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> insertRecomment(
            @RequestParam(name = "cono") Long cono,
            @ModelAttribute RecommentEntity recomment,
            @RequestHeader(name = "token") String token) {

        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            String uid = jwtUtil.extractUsername(token);

            CommEntity comm = new CommEntity();
            comm.setCono(cono);
            
            CommEntity commEnt = commRepository2.findById(cono).orElse(null);
            // System.out.println(commEnt.getBoard().getBno());
           
            MemberEntity member = new MemberEntity();
            member.setUid(uid);

            recomment.setComm(comm);
            recomment.setMember(member);

            int ret = cService2.insertRecomm(recomment);

            if (ret == 1) {
                map.put("status", 200);
                try {
                    // 알림 DB 저장 호출
                    AlertEntity alert = new AlertEntity();
                    alert.setAltype(4L);
                    alert.setAlurl("/ROOT/api/board/selectone?bno=" + commEnt.getBoard().getBno());
                    Long cLong = comm.getCono();
                    // System.out.println(cLong);
                    CommEntity cEntity = commRepository2.getById(cLong);
                    // System.out.println(cEntity.getMember().getUid());
                    String commUid = cEntity.getMember().getUid();
                    MemberEntity mement = new MemberEntity();
                    mement.setUid(commUid);
                    alert.setMember(mement);
                    
                    alertServiceImpl3.insertAlert(alert);

                    // 여기에 알림 호출 (대댓글 단 해당 댓글 회원에게 알림)
                    alertServiceImpl3.sendRecommentAlert(comm, alert);

                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("답변호출에러===>"+e);
                    map.put("status", 100);
                }                
            }

        } catch (

        Exception e) {
            e.printStackTrace();
            map.put("status", -1);

        }
        return map;
    }

    // 대댓글 삭제
    // 127.0.0.1:9090/ROOT/api/comm/deleteRecomment
    @RequestMapping(value = "/deleteRecomment", method = { RequestMethod.DELETE }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> deleteRecomment(
            @RequestParam(name = "reno") Long reno,
            @RequestHeader(name = "token") String token) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);

        try {
            String uid = jwtUtil.extractUsername(token);

            int ret = cService2.deleteRecomm(uid, reno);

            if (ret == 1) {
                map.put("status", 200);
                map.put("msg", "삭제 완료");
            }
            if (ret == 0) {
                map.put("msg", "본인 대댓글 아니거나 없는 대댓글번호");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // 게시글 수정
    // 127.0.0.1:9090/ROOT/api/comm/updateRecomment
    @RequestMapping(value = "/updateRecomment", method = { RequestMethod.PUT }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> updateRecomment(
            @RequestHeader(name = "token") String token,
            @ModelAttribute RecommentEntity recomm) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);

        try {
            String uid = jwtUtil.extractUsername(token);

            int ret = cService2.updateRecomm(uid, recomm);
            if (ret == 1) {
                map.put("status", 200);
            }

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // 대댓글 목록 조회
    @RequestMapping(value = { "/selectlistRecomment" }, method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> SelectListRecomment(
            @RequestParam(value = "cono") Long cono,
            @RequestHeader(value = "token", required = false) String token) {

        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);

        try {
            List<RecommentEntity> list = cService2.selectListRecomm(cono);

            // 로그인 시
            if (token != null) {
                String uid = jwtUtil.extractUsername(token);

                for (RecommentEntity recomm : list) {
                    if (recomm.getRereopen() == 2L) {
                        // 대댓글 작성자이거나, 댓글작성자
                        // 비밀대댓글을 확인 가능한 권한
                        if (recomm.getMember().getUid().equals(uid) ||
                                recomm.getComm().getMember().getUid().equals(uid)) {
                            recomm.setRereopen(3L);
                        }
                        // 비밀대댓글 확인 불가능한 권한
                        // '비밀댓글입니다' 작업하기
                        else {
                            recomm.setRereopen(4L);
                        }
                    }
                }
            }
            // 비로그인 시
            else {
                for (RecommentEntity recomm : list) {
                    if (recomm.getRereopen() == 2L) {
                        // 비밀대댓글 확인 불가능한 권한
                        // '비밀댓글입니다' 작업하기
                        recomm.setRereopen(4L);
                    }
                }
            }
            if (list != null) {
                map.put("status", 200);
                map.put("list", list);
            }

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // 자기가 쓴 글 표시하기
    // 127.0.0.1:9090/ROOT/api/comm/checkMineRecomm?rono=25
    @RequestMapping(value = { "/checkMineRecomm" }, method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> checkMineRecomm(
            @RequestParam(name = "reno") Long reno,
            @RequestHeader(name = "token", required = false) String token) {

        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);

        try {
            if (token != null) {
                String uid = jwtUtil.extractUsername(token);
                int ret = cService2.checkRecommMine(reno, uid);
                if (ret == 1) {
                    map.put("status", 200);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

}
