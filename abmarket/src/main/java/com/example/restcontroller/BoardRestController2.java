package com.example.restcontroller;

import java.util.HashMap;
import java.util.Map;

import com.example.entity.AlertEntity;
import com.example.entity.BoardEntity;
import com.example.entity.BoardInterest;
import com.example.entity.InterestEntity;
import com.example.entity.MemberEntity;
import com.example.entity.MeminterestEntity;
import com.example.jwt.JwtUtil;
import com.example.repository.BoardInterestRepository2;
import com.example.repository.BoardRepository1;
import com.example.repository.InterestRepository1;
import com.example.service.AlertServiceImpl3;
import com.example.service.BoardService1;
import com.example.service.MemInterestService1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/board")
public class BoardRestController2 {

    @Autowired
    BoardService1 bService1;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    BoardInterestRepository2 boardInterestRepository2;

    @Autowired
    MemInterestService1 memInterestService1;

    @Autowired
    AlertServiceImpl3 alertServiceImpl3;

    @Autowired
    BoardRepository1 boardRepository1;

    @Autowired
    InterestRepository1 interestRepository1;

    // 127.0.0.1:9090/ROOT/api/board/insertBnoTag?bno=2
    // 게시판에 관심사 태그 설정하기
    @RequestMapping(value = "/insertBnoTag", method = { RequestMethod.POST }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> insertBnoTag(
            @RequestHeader(name = "token") String token,
            @RequestParam(name = "inname") String inname) {
        Map<String, Object> map = new HashMap<>();
        try {
            // System.out.println(bEntity);
            String userid = jwtUtil.extractUsername(token);
            // System.out.println("userid =>" + userid);

            BoardEntity board = boardRepository1.findTop1ByMember_uidOrderByBregdateDesc(userid);
            InterestEntity interestEntity = interestRepository1.findByInname(inname);

            BoardInterest boardInterest = new BoardInterest();

            boardInterest.setBoard(board);
            boardInterest.setInterest(interestEntity);

            boardInterestRepository2.save(boardInterest);

            Long incodeList = interestEntity.getIncode();
            MeminterestEntity memInt = memInterestService1.selectListInt2(incodeList);

            // 관심사 관련 알림 설정 추가
            // 회원관심사 마다 알림이 다름
            // 전체 회원이 설정한 관심사 중 알림 on (1) & 게시판 관심사 확인
            // 알림 온오프 확인
            // 알림 ON
            // 멤버 인터레스트가 없을 경우 null
            if (memInt != null) {
                if (memInt.getMialert() == 1L) {
                    // System.out.println("확인1===" + memInt.getInterest().getIncode());
                    // 게시판 관심사(BOARDINTEREST) = 회원 관심사(MEMINTEREST)
                    Long memIntEntIncode = memInt.getInterest().getIncode();
                    if (memIntEntIncode == boardInterest.getInterest().getIncode()) {
                        // System.out.println("확인2===" + boardInterest.getInterest().getIncode());
                        try {
                            // 알림 DB 저장 호출
                            // 타입, url, 아이디 설정
                            AlertEntity alertEnt = new AlertEntity();
                            alertEnt.setAltype(7L);
                            // 해당 문의글 url
                            alertEnt.setAlurl("/ROOT/api/board/selectone?bno=" + boardInterest.getBoard().getBno());
                            // 해당 회원 아이디 (여기에선 여러명)
                            MemberEntity memEnt = new MemberEntity();
                            memEnt.setUid(memInt.getMember().getUid()); // String uid
                            alertEnt.setMember(memEnt); // 멤버 엔티티

                            alertServiceImpl3.insertAlert(alertEnt);

                            // 관심사 알림 on 중 게시판 관심사와 같은 회원에게 알림 호출
                            alertServiceImpl3.sendInterestAlert(memInt, alertEnt);

                        } catch (Exception e) {
                            e.printStackTrace();
                            // System.out.println("답변호출에러===>" + e);
                        }
                    }
                }
            }
            map.put("status", 200);
        } catch (Exception e) {
            map.put("status", -1);
            e.printStackTrace();
        }
        return map;
    }

}
