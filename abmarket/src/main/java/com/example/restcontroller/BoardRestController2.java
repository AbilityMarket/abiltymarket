package com.example.restcontroller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.entity.AlertEntity;
import com.example.entity.BoardEntity;
import com.example.entity.BoardInterest;
import com.example.entity.InterestEntity;
import com.example.entity.MemberEntity;
import com.example.entity.MeminterestEntity;
import com.example.jwt.JwtUtil;
import com.example.repository.BoardInterestRepository2;
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
    

    // 127.0.0.1:9090/ROOT/api/board/insertBnoTag?bno=2
    // 게시판에 관심사 태그 설정하기
    @RequestMapping(value = "/insertBnoTag", method = { RequestMethod.POST }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> insertBnoTag(
            @RequestHeader(name = "token") String token,
            @RequestParam(name = "bno") Long bno,
            @RequestParam(name = "incode") Long incode[]) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            //토큰 필요함(토큰 추출) // 작성자 토큰
            String userid = jwtUtil.extractUsername(token);
            System.out.println("RequestMapping username : " + userid);

            BoardEntity board = new BoardEntity();
            board.setBno(bno);
            int count = 0;
            for (int i = 0; i < incode.length; i++) {
                BoardInterest boardInterest = new BoardInterest();
                boardInterest.setBoard(board);
                InterestEntity interest = new InterestEntity();
                interest.setIncode(incode[i]);
                boardInterest.setInterest(interest);
                boardInterestRepository2.save(boardInterest);

                // 관심사 관련 알림 설정 추가
                // 회원관심사 마다 알림이 다름
                // 회원이 설정한 관심사 중 알림 on (1) & 게시판 관심사 확인
                // ******************** 다시 수정 *****************************
                //List<MeminterestEntity> meminterestEntity = new ArrayList<>();
                // ((BoardInterest) meminterestEntity).setInterest(interest);
                // meminterestEntity.getMember().getUid();
                // System.out.println(meminterestEntity.getMember().getUid());

                List<MeminterestEntity> memUserid = memInterestService1.selectListMemInt(userid); //회원 토큰(작성자X)
                List<Long> list = new ArrayList<Long>();
                for(MeminterestEntity memIntEnt : memUserid) {
                    list.add(memIntEnt.getMialert());
                    //map.put("list", list);
                    if(memIntEnt.getMialert() == 1L) {
                        System.out.println(memIntEnt.getInterest().getInname()); // 농구 (선택한 관심사 중 알림 on)
    
                        // 게시판 관심사(BOARDINTEREST) = 회원 관심사(MEMINTEREST)
                        String inname = memIntEnt.getInterest().getInname();
                        if(inname.equals(boardInterest.getInterest().getInname())) {
                            //System.out.println(boardInterest.getInterest().getInname());
                            try {
                                // 알림 DB 저장 호출
                                // 타입, url, 아이디 설정
                                AlertEntity alertEnt = new AlertEntity();
                                alertEnt.setAltype(7L);
                                // 해당 문의글 url
                                alertEnt.setAlurl("/ROOT/api/board/selectone?bno=" + boardInterest.getBoard().getBno());
                                // 해당 회원 아이디
                                MemberEntity memEnt = new MemberEntity();
                                memEnt.setUid(memIntEnt.getMember().getUid()); // String uid
                                alertEnt.setMember(memEnt); // 멤버 엔티티

                                alertServiceImpl3.insertAlert(alertEnt);

                                // 관심사 알림 on 중 게시판 관심사와 같은 회원에게 알림 호출
                                alertServiceImpl3.sendInterestAlert(boardInterest, alertEnt);
                                
                            } catch (Exception e) {
                                e.printStackTrace();
                                System.out.println("답변호출에러===>"+e);
                            }
                        }
                    }
                }
                if (boardInterest != null) {
                    count += 1;
                }
            }
            if (count == incode.length) {
                map.put("status", 200);
            }
        } catch (Exception e) {
            map.put("status", -1);
            e.printStackTrace();
        }
        return map;
    }

}
