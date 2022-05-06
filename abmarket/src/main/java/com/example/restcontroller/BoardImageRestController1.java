package com.example.restcontroller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.entity.BoardEntity;
import com.example.entity.BoardImageEntity;
import com.example.entity.BoardImageEntityProjection;
import com.example.jwt.JwtUtil;
import com.example.service.BoardImageService1;
import com.example.service.BoardService1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/api/boardimg")
public class BoardImageRestController1 {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    BoardService1 bService1;

    @Autowired
    BoardImageService1 boardimgService1;

    // 이미지 없을 때 처리
    @Autowired
    ResourceLoader resLoader;

    // 서브이미지 등록
    // 127.0.0.1:9090/ROOT/api/boardimg/insert?bno=1
    @RequestMapping(value = "/insert", method = { RequestMethod.POST }, consumes = {
            MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> insertPost(
            @RequestHeader(name = "token") String token,
            @RequestParam(name = "file", required = false) MultipartFile file[],
            @RequestParam(name = "bno") Long bno) {

        Map<String, Object> map = new HashMap<>();

        try {
            String userid = jwtUtil.extractUsername(token);
            System.out.println("userid =>" + userid);

            List<BoardImageEntity> list = new ArrayList<>();
            for (int i = 0; i < file.length; i++) {
                if (file != null) {
                    if (!file[i].isEmpty()) {
                        BoardImageEntity boardimage1 = new BoardImageEntity();
                        boardimage1.setBimage(file[i].getBytes());
                        boardimage1.setBimagename(file[i].getOriginalFilename());
                        boardimage1.setBimagesize(file[i].getSize());
                        boardimage1.setBimagetype(file[i].getContentType());

                        BoardEntity bEntity = new BoardEntity();
                        bEntity.setBno(bno);
                        System.out.println(bEntity.toString());
                        boardimage1.setBoard(bEntity);

                        list.add(boardimage1);
                    }
                }
            }
            int ret = boardimgService1.insertBoardImage(list);
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

    // 서브이미지 가져오기
    // 127.0.0.1:9090/ROOT/api/boardimg/image?bino=8
    @GetMapping(value = "/image") // url 주소생성
    public ResponseEntity<byte[]> imageGET(
            @RequestParam(name = "bino") long bino)
            throws IOException {

        // 이미지명, 이미지크기, 이미지종류, 이미지데이터
        BoardImageEntity boardImage = boardimgService1.selectBoardImage(bino);
        System.out.println(boardImage.getBimagename());

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

    // 게시물 번호에 해당하는 서브이미지 조회(번호에 해당하는것) - 이게 있어야 화면이 출력됨
    // 127.0.0.1:9090/ROOT/api/boardimg/select?bino=1
    @RequestMapping(value = "/select", method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> selectOneGET(@RequestParam(name = "bno") long bno) {
        Map<String, Object> map = new HashMap<>();
        System.out.println(bno);
        map.put("status", 0);

        try {
            List<BoardImageEntityProjection> list = boardimgService1.selectBoardImageProjection(bno);
            if (list != null) {
                List<String> list1 = new ArrayList<String>();

                for (BoardImageEntityProjection obj : list) {
                    list1.add("/ROOT/api/boardimg/image?bino=" + obj.getBino());
                }
                map.put("status", 200);
                map.put("list", list1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // 서브이미지 일괄수정
    // 127.0.0.1:9090/ROOT/api/boardimg/update?bino=1&bino=2
    @RequestMapping(value = "/update", method = { RequestMethod.PUT }, consumes = {
            MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> updatePost(
            @RequestHeader(name = "token") String token,
            @RequestParam(name = "file", required = false) MultipartFile file[],
            @RequestParam(name = "bino") Long[] bino) {
        Map<String, Object> map = new HashMap<>();

        try {
            String userid = jwtUtil.extractUsername(token);
            System.out.println("userid :" + userid);

            List<BoardImageEntity> list = new ArrayList<>();
            for (int i = 0; i < file.length; i++) {
                if (file != null) {
                    if (!file[i].isEmpty()) {
                        BoardImageEntity boardimage1 = boardimgService1.selectBoardImage(bino[i]);
                        System.out.println(boardimage1.toString());
                        boardimage1.setBimage(file[i].getBytes());
                        boardimage1.setBimagename(file[i].getOriginalFilename());
                        boardimage1.setBimagesize(file[i].getSize());
                        boardimage1.setBimagetype(file[i].getContentType());

                        list.add(boardimage1);
                    }
                }
            }
            int ret = boardimgService1.updateBoardImage(list);
            if (ret == 1) {
                map.put("status", 200);
                map.put("result", "일괄수정완료");
            } else {
                map.put("status", 0);
            }

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // 게시물 안에 있는 서브이미지 일괄삭제
    @RequestMapping(value = "/deletebatch", method = { RequestMethod.DELETE }, consumes = {
            MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> deletePost(
            @RequestHeader(name = "token") String token,
            @RequestParam(name = "bno") long bno) {

        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);

        try {
            String userid = jwtUtil.extractUsername(token);
            System.out.println("TOKEN :" + userid);
            BoardEntity board1 = bService1.selectBoardOne(bno);
            System.out.println(board1.toString());

            if (userid.equals(board1.getMember().getUid())) {
                List<BoardImageEntityProjection> list = boardimgService1.selectBoardImageProjection(bno);

                int ret = 0;
                for (BoardImageEntityProjection board : list) {
                    ret += boardimgService1.deleteBoardImageBatch(board.getBino());
                }

                if (ret == list.size()) {
                    map.put("status", 200);
                } else {
                    map.put("status", 0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // 서브이미지 부분삭제
    // 127.0.0.1:9090/ROOT/api/boardimg/delete?bino=1&bino=1
    @RequestMapping(value = "/delete", method = { RequestMethod.DELETE }, consumes = {
            MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> deletePost(
            @RequestHeader(name = "token") String token,
            @RequestParam(name = "bino") long[] bino) {

        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);

        try {
            String userid = jwtUtil.extractUsername(token);
            System.out.println("TOKEN :" + userid);

            if (userid != null) {

                int ret = boardimgService1.deleteBoardImage(bino);
                if (ret == 1) {
                    map.put("status", 200);
                } else {
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
