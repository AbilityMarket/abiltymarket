package com.example.restcontroller;

import java.util.HashMap;
import java.util.Map;

import com.example.entity.MemberEntity;
import com.example.entity.RankEntity;
import com.example.jwt.JwtUtil;
import com.example.service.RankService1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
    JwtUtil jwtUtil;

    @Autowired
    RankService1 rService1;

    // 등급내용 작성하기
    // 127.0.0.1:9090/ROOT/api/rank/insert
    @RequestMapping(value = "/insert", method = { RequestMethod.POST }, consumes = { MediaType.ALL_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> insertPost(
            @ModelAttribute RankEntity rankEntity,
            @RequestParam(name = "file", required = false) MultipartFile file,
            // 나중에 required 풀기
            @RequestHeader(name = "token") String token) {

        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            String uid = jwtUtil.extractUsername(token);
            System.out.println("admin =>" + uid);

            // MemberEntity mEntity = new MemberEntity();
            // mEntity.setUid(userid);

            if (file != null) {
                rankEntity.setRimage(file.getBytes());
                rankEntity.setRimagename(file.getOriginalFilename());
                rankEntity.setRimagesize(file.getSize());
                rankEntity.setRimagetype(file.getContentType());
            }

            int ret = rService1.insertRank(rankEntity);
            if (ret == 1) {
                map.put("status", 200);
                map.put("result", "작성완료");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // 회원 등급 조회하기
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
                map.put("result", retRank.getRname());
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
    @RequestMapping(value = "/update", method = { RequestMethod.PUT }, consumes = { MediaType.ALL_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> updatePost(
            @ModelAttribute RankEntity rEntity,
            @RequestHeader(name = "token") String token) {

        Map<String, Object> map = new HashMap<>();

        try {
            String admin = jwtUtil.extractUsername(token);
            System.out.println("admin =>" + admin);

            // MemberEntity mEntity = new MemberEntity();
            // mEntity.setUid(userid);

            RankEntity rEntity1 = rService1.selectRank(rEntity.getRname());
            rEntity1.setRcontent(rEntity.getRcontent());

            int ret = rService1.updateRank(rEntity1);
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

    // 등급내용 삭제하기
    @RequestMapping(value = "/delete", method = { RequestMethod.DELETE }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> deletePost(
            @RequestHeader(name = "token") String token,
            @RequestParam(name = "rname") String rname) {

        Map<String, Object> map = new HashMap<>();

        try {
            String admin = jwtUtil.extractUsername(token);
            System.out.println("admin =>" + admin);

            // MemberEntity mEntity = new MemberEntity();
            // mEntity.setUid(admin);

            int ret = rService1.deleteRank(rname);
            if (ret == 1) {
                map.put("status", 200);
                map.put("result", "삭제완료");
            } else {
                map.put("status", 0);
                map.put("result", "삭제실패");
            }

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // 이미지 등록
    // 127.0.0.1:9090/ROOT/api/rank/insertimage
    @RequestMapping(value = "/insertimage", method = { RequestMethod.POST }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> insertPost(
            @RequestHeader(name = "token") String token,
            @ModelAttribute RankEntity rEntity,
            @RequestParam(name = "file") MultipartFile file) {

        Map<String, Object> map = new HashMap<>();

        try {
            String admin = jwtUtil.extractUsername(token);
            System.out.println("admin =>" + admin);

            if (file != null) {
                rEntity.setRimage(file.getBytes());
                rEntity.setRimagename(file.getOriginalFilename());
                rEntity.setRimagesize(file.getSize());
                rEntity.setRimagetype(file.getContentType());
            }

            long ret = rService1.insertRankImage(rEntity);
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

    // 이미지 가져오기
    @RequestMapping(value = "/selectimage", method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> selectoneGET(
            @RequestParam(name = "rname") String rname) {
        Map<String, Object> map = new HashMap<>();

        try {
            RankEntity rEntity = rService1.selectRankImage(rname);
            if (rEntity != null) {
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

    // 이미지 수정
    // @RequestMapping(value = "update", method = { RequestMethod.PUT }, consumes =
    // { MediaType.ALL_VALUE }, produces = {
    // MediaType.APPLICATION_JSON_VALUE })
    // public Map<String, Object> updatePost(
    // @RequestHeader(name = "token") String token,
    // @ModelAttribute RankEntity rEntity,
    // @RequestParam(name = "file") MultipartFile file) {

    // Map<String, Object> map = new HashMap<>();

    // try {
    // String admin = jwtUtil.extractUsername(token);
    // System.out.println("admin =>" + admin);

    // if (file != null) {
    // RankEntity rEntity1 = rService1.selectRankImage(rEntity.getRname());
    // rEntity1.setRimage(file.getBytes());
    // rEntity1.setRimagename(file.getOriginalFilename());
    // rEntity1.setRimagesize(file.getSize());
    // rEntity1.setRimagetype(file.getContentType());

    // int ret = rService1.updateRankImage(rEntity1);
    // if (ret == 1) {
    // map.put("status", 200);
    // map.put("result", "수정완료");
    // } else {
    // map.put("status", 0);
    // map.put("status", "수정실패");
    // }
    // }
    // } catch (Exception e) {
    // e.printStackTrace();
    // map.put("status", -1);
    // }
    // return map;
    // }

    // 이미지 삭제
    @RequestMapping(value = "/deleteimage", method = { RequestMethod.DELETE }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> deleteimagePost(
            @RequestHeader(name = "token") String token,
            @RequestParam(name = "rname") String rname) {

        Map<String, Object> map = new HashMap<>();

        try {
            String admin = jwtUtil.extractUsername(token);
            System.out.println("admin =>" + admin);

            int ret = rService1.deleteRankImage(rname);
            if (ret == 1) {
                map.put("status", 200);
                map.put("status", "삭제성공");
            } else {
                map.put("status", 0);
                map.put("status", "삭제실패");
            }

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

}
