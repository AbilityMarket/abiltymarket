package com.example.restcontroller;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.example.entity.MemberEntity;
import com.example.entity.RankEntity;
import com.example.entity.RrrankEntity;
import com.example.jwt.JwtUtil;
import com.example.repository.MemberRespository2;
import com.example.repository.RankRepository1;
import com.example.repository.RrrankRepository2;
import com.example.service.RankService1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping(value = "/api/rank")
@RestController
public class RankRestController1 {

    @Autowired
    ResourceLoader resLoader;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    RankService1 rService1;

    @Autowired
    MemberRespository2 memRepository2;

    @Autowired
    RrrankRepository2 rrrankRepository2;

    @Autowired
    RankRepository1 rankRepository1;

    // 등급내용 작성하기
    // 127.0.0.1:9090/ROOT/api/rank/insert
    @RequestMapping(value = "/insert", method = { RequestMethod.POST }, consumes = { MediaType.ALL_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> insertPost(
            @ModelAttribute RankEntity rankEntity,
            @RequestParam(name = "file", required = false) MultipartFile file,
            // 나중에 required 풀기
            @RequestHeader(name = "token", required = false) String token) {

        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            String admin = jwtUtil.extractUsername(token);
            System.out.println("adminId =>" + admin);
            MemberEntity member = memRepository2.getById(admin);

            if (file != null) {
                if (!file.isEmpty()) {
                    rankEntity.setRimage(file.getBytes());
                    rankEntity.setRimagename(file.getOriginalFilename());
                    rankEntity.setRimagesize(file.getSize());
                    rankEntity.setRimagetype(file.getContentType());
                }
            }
            rankEntity.setRcontent(rankEntity.getRcontent());

            // 관리자 설정
            if (member.getUrole().equals("ADMIN")) {
                int ret = rService1.insertRank(rankEntity);
                if (ret == 1) {
                    map.put("status", 200);
                    map.put("result", "작성완료");
                } else {
                    map.put("status", 0);
                }
            } else {
                map.put("status", 0);
                map.put("result", "관리자권한");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // 회원 등급 조회하기
    // 127.0.0.1:9090/ROOT/api/rank/selectone
    @RequestMapping(value = "/selectone", method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> selectGET(
            @RequestParam(name = "rname") String rname) {

        Map<String, Object> map = new HashMap<>();

        try {
            MemberEntity mEntity = new MemberEntity();
            mEntity.setUid(mEntity.getUid());

            RankEntity retRank = rService1.selectRank(rname);
            if (retRank != null) {
                map.put("status", 200);
                map.put("result", retRank.getRname() + "등급");
            } else {
                map.put("status", 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // 등급내용 수정하기
    // 127.0.0.1:9090/ROOT/api/rank/update
    @RequestMapping(value = "/update", method = { RequestMethod.PUT }, consumes = { MediaType.ALL_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> updatePost(
            @ModelAttribute RankEntity rankEntity,
            @RequestParam(name = "file", required = false) MultipartFile file,
            @RequestHeader(name = "token") String token) {

        Map<String, Object> map = new HashMap<>();

        try {
            String admin = jwtUtil.extractUsername(token);
            System.out.println("adminId =>" + admin);
            MemberEntity member = memRepository2.getById(admin);

            if (member.getUrole().equals("ADMIN")) {
                RankEntity rankEntity1 = rService1.selectRank(rankEntity.getRname());
                rankEntity1.setRimage(file.getBytes());
                rankEntity1.setRimagename(file.getOriginalFilename());
                rankEntity1.setRimagesize(file.getSize());
                rankEntity1.setRimagetype(file.getContentType());

                rankEntity1.setRcontent(rankEntity.getRcontent());

                int ret = rService1.updateRank(rankEntity1);
                if (ret == 1) {
                    map.put("status", 200);
                    map.put("reslut", "수정완료");
                } else {
                    map.put("status", 0);
                }
            } else {
                map.put("status", 0);
                map.put("result", "관리자권한");
            }

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // 등급내용 삭제하기
    // 127.0.0.1:9090/ROOT/api/rank/delete
    @RequestMapping(value = "/delete", method = { RequestMethod.DELETE }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> deletePost(
            @RequestHeader(name = "token") String token,
            @RequestParam(name = "rname") String rname) {

        Map<String, Object> map = new HashMap<>();

        try {
            String admin = jwtUtil.extractUsername(token);
            System.out.println("adminId =>" + admin);
            MemberEntity member = memRepository2.getById(admin);

            if (member.getUrole().equals("ADMIN")) {
                int ret = rService1.deleteRank(rname);

                if (ret == 1) {
                    map.put("status", 200);
                    map.put("result", "삭제완료");
                } else {
                    map.put("status", 0);
                }
            } else {
                map.put("status", 0);
                map.put("result", "관리자권한");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // uid로 멤버 이미지 조회하기
    // 127.0.0.1:9090/ROOT/api/member/image?uid=uid
    @RequestMapping(value = "/image") // url 주소생성
    public ResponseEntity<byte[]> imageGET(
            @RequestParam(name = "uid") String uid)
            throws IOException {

        RrrankEntity rankinfo = rrrankRepository2.findByMember_uid(uid);
        RankEntity rank = rankRepository1.findById(rankinfo.getRank().getRname()).orElse(null);

        // 이미지가 있을때
        if (rank.getRimagesize() > 0) { // 첨부한 파일 존재
            HttpHeaders headers = new HttpHeaders();

            if (rank.getRimagetype().equals("image/jpeg")) {
                headers.setContentType(MediaType.IMAGE_JPEG);
            } else if (rank.getRimagetype().equals("image/png")) {
                headers.setContentType(MediaType.IMAGE_PNG);
            } else if (rank.getRimagetype().equals("image/gif")) {
                headers.setContentType(MediaType.IMAGE_GIF);
            }

            // 이미지 byte[], headers, HttpStatus.Ok
            ResponseEntity<byte[]> response = new ResponseEntity<>(rank.getRimage(),
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