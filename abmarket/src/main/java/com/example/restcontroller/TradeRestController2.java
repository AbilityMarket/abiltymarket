package com.example.restcontroller;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.entity.BoardAndWriter;
import com.example.entity.BoardEntity;

import com.example.repository.BoardAndWriterRepository2;
import com.example.repository.BoardImageRepository1;
import com.example.repository.BoardRepository2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/trade")
public class TradeRestController2 {

    @Autowired
    BoardAndWriterRepository2 boardAndWriterRepository2;

    @Autowired
    BoardRepository2 boardRepository2;

    @Autowired
    BoardImageRepository1 boardImageRepository1;

    @Autowired
    ResourceLoader resLoader;

    // 구매 목록
    @RequestMapping(value = "/helpMe", method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> HelpmeGET(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "brole") Long brole,
            @RequestParam(name = "incategory") String incategory,
            @RequestParam(name = "inname") String inname) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);

        try {
            // System.out.println(page);
            // System.out.println(brole);
            // System.out.println(incategory);
            // System.out.println(inname);
            Pageable pageable = PageRequest.of(page - 1, 12);
            if (inname.equals("전체")) {
                List<BoardAndWriter> list = boardAndWriterRepository2
                        .findByBroleAndIncategoryOrderByBregdateDesc(pageable, brole,
                                incategory);
                Long count = boardAndWriterRepository2.countByBroleAndIncategoryOrderByBregdateDesc(brole,
                        incategory);
                if (list.size() > 0 || count == 0) {
                    map.put("list", list);
                    map.put("status", 200);
                    map.put("page", count / 12 + 1);
                }
            } else {
                List<BoardAndWriter> list = boardAndWriterRepository2
                        .findByBroleAndIncategoryAndInnameOrderByBregdateDesc(pageable, brole,
                                incategory, inname);
                Long count = boardAndWriterRepository2.countByBroleAndIncategoryAndInnameOrderByBregdateDesc(brole,
                        incategory, inname);
                if (list.size() > 0 || count == 0) {
                    map.put("list", list);
                    map.put("status", 200);
                    map.put("page", count / 12 + 1);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // 이미지 가져오기
    // 127.0.0.1:9090/ROOT/api/trade/image?bno=8
    @GetMapping(value = "/image") // url 주소생성
    public ResponseEntity<byte[]> imageGET(
            @RequestParam(name = "bno") long bno)
            throws IOException {

        // 이미지명, 이미지크기, 이미지종류, 이미지데이터
        BoardEntity boardImage = boardRepository2.findById(bno).orElse(null);
        // System.out.println(boardImage.getBimagename());

        // 이미지가 있을때
        if (boardImage.getBimagesize() > 0) { // 첨부한 파일 존재
            HttpHeaders headers = new HttpHeaders();

            if (boardImage.getBimagetype().equals("image/jpeg")) {
                headers.setContentType(MediaType.IMAGE_JPEG);
            } else if (boardImage.getBimagetype().equals("image/png")) {
                headers.setContentType(MediaType.IMAGE_PNG);
            } else if (boardImage.getBimagetype().equals("image/gif")) {
                headers.setContentType(MediaType.IMAGE_GIF);
            }

            // 이미지 byte[], headers, HttpStatus.Ok
            ResponseEntity<byte[]> response = new ResponseEntity<>(boardImage.getBimage(),
                    headers, HttpStatus.OK);
            return response;
        } else { // 이미지 없을때
            InputStream is = resLoader
                    .getResource("classpath:/static/images/default.jpg")
                    .getInputStream();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);

            ResponseEntity<byte[]> response = new ResponseEntity<>(is.readAllBytes(),
                    headers, HttpStatus.OK);

            return response;
        }
    }
}
