package com.example.restcontroller;

import java.util.HashMap;
import java.util.Map;

import com.example.entity.BoardEntity;
import com.example.entity.BoardInterest;
import com.example.entity.InterestEntity;
import com.example.jwt.JwtUtil;
import com.example.repository.BoardInterestRepository2;
import com.example.service.BoardService1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

    // 127.0.0.1:9090/ROOT/api/board/insertBnoTag?bno=2
    // 게시판에 관심사 태그 설정하기
    @RequestMapping(value = "/insertBnoTag", method = { RequestMethod.POST }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> insertBnoTag(
            @RequestParam(name = "bno") Long bno,
            @RequestParam(name = "incode") Long incode[]) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
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
