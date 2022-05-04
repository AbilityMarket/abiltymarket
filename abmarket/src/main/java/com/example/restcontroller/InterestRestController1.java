package com.example.restcontroller;

import java.util.HashMap;
import java.util.Map;

import com.example.entity.InterestEntity;
import com.example.entity.MemberEntity;
import com.example.entity.MeminterestEntity;
import com.example.jwt.JwtUtil;
import com.example.service.InterestService1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/api/interest")
public class InterestRestController1 {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    InterestService1 intService1;

    // 관심사 등록하기
    // 127.0.0.1:9090/ROOT/api/interest/insert
    @RequestMapping(value = "/insert", method = { RequestMethod.POST }, consumes = { MediaType.ALL_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> insertPost(
            @ModelAttribute InterestEntity intEntity,
            @RequestHeader(name = "token") String token) {

        Map<String, Object> map = new HashMap<>();

        try {
            String userid = jwtUtil.extractUsername(token);
            System.out.println("userid =>" + userid);

            MemberEntity mEntity = new MemberEntity();
            mEntity.setUid(userid);

            // 관심사테이블
            InterestEntity iEntity = new InterestEntity();
            iEntity.setIncode(1L);

            MeminterestEntity miEntity = new MeminterestEntity();
            miEntity.setMember(mEntity);
            miEntity.setInterest(iEntity);
            miEntity.setMialert(0L);

            int ret = intService1.insertInterest(intEntity);
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

    // 관심사 조회하기
    // 127.0.0.1:9090/ROOT/api/interest/selectone?incode=1
    @RequestMapping(value = "/selectone", method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> selectOneGET(
            @RequestParam(name = "incode") long incode) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);

        try {
            InterestEntity retInterest = intService1.selectOneInterest(incode);
            if (retInterest != null) {
                map.put("status", 200);
                map.put("result", retInterest);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("value", -1);
        }
        return map;
    }

    // 관심사 수정하기
    // 127.0.0.1:9090/ROOT/api/interest/update
    @RequestMapping(value = "/update", method = { RequestMethod.PUT }, consumes = { MediaType.ALL_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> updatePost(
            @RequestHeader(name = "token") String token,
            @ModelAttribute InterestEntity intEntity) {

        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        System.out.println("TOKEN =>" + token);
        System.out.println("intEntity :" + intEntity.toString());

        try {
            String userid = jwtUtil.extractUsername(token);
            System.out.println(userid);

            MemberEntity mEntity = new MemberEntity();
            mEntity.setUid(userid);

            InterestEntity intEntity1 = intService1.selectOneInterest(intEntity.getIncode());
            System.out.println(intEntity1.toString());
            intEntity1.setIncategory(intEntity.getIncategory());
            intEntity1.setInname(intEntity.getInname());

            int ret = intService1.updateInterest(intEntity1);
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

    // 관심사 삭제하기
    // 127.0.0.1:9090/ROOT/api/interest/delete?incode=1
    @RequestMapping(value = "/delete", method = { RequestMethod.DELETE }, consumes = {
            MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> deletePost(
            @RequestHeader(name = "token") String token,
            @RequestParam(name = "incode") long incode) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);

        try {
            int ret = intService1.deleteInterest(incode);
            if (ret == 1) {
                map.put("status", 200);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("value", -1);
        }
        return map;
    }

    // 이미지 등록
    // 127.0.0.1:9090/ROOT/api/interest/insertimage
    @RequestMapping(value = "/insertimage", method = { RequestMethod.POST }, consumes = {
            MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> insertPost(
            @RequestHeader(name = "token") String token,
            @ModelAttribute InterestEntity intEntity,
            @RequestParam(name = "file") MultipartFile file
    // ,@RequestParam(name = "micode") Long micode
    ) {

        Map<String, Object> map = new HashMap<>();

        try {
            String userid = jwtUtil.extractUsername(token);
            System.out.println("userid =>" + userid);

            if (file != null) {
                intEntity.setInimage(file.getBytes());
                intEntity.setInimagename(file.getOriginalFilename());
                intEntity.setInimagesize(file.getSize());
                intEntity.setInimagetype(file.getContentType());

                // MeminterestEntity MemIEntity = new MeminterestEntity();
                // MemIEntity.setMicode(micode);
                // System.out.println(micode.toString());
                // intEntity.setMeminterestList(meminterestList);

                long ret = intService1.insertInterestImage(intEntity);
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

    // 이미지 가져오기(1개)
    // 127.0.0.1:9090/ROOT/api/interest/selectimageone?incode=1
    @RequestMapping(value = "/selectimageone", method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> selectImageOneGET(
            @RequestParam(name = "rname") long incode) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);

        try {
            InterestEntity retInterest = intService1.selectOneInterestImage(incode);
            if (retInterest != null) {
                map.put("status", 200);
                map.put("result", retInterest.getIncode());
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // 이미지 수정
    // 127.0.0.1:9090/ROOT/api/interest/updateimage?incode=1
    @RequestMapping(value = "/updateimage", method = { RequestMethod.PUT }, consumes = {
            MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> updateImagePost(
            @RequestHeader(name = "token") String token,
            @ModelAttribute InterestEntity intEntity,
            @RequestParam(name = "file") MultipartFile file) {
        Map<String, Object> map = new HashMap<>();

        try {
            String userid = jwtUtil.extractUsername(token);
            System.out.println("userid =>" + userid);

            InterestEntity intEntity1 = intService1.selectOneInterestImage(intEntity.getIncode());
            System.out.println(intEntity1.toString());
            intEntity1.setInimage(file.getBytes());
            intEntity1.setInimagename(file.getOriginalFilename());
            intEntity1.setInimagesize(file.getSize());
            intEntity1.setInimagetype(file.getContentType());

            int ret = intService1.updateInterestImage(intEntity1);
            if (ret == 1) {
                map.put("status", 200);
            }

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // 이미지 삭제
    // 127.0.0.1:9090/ROOT/api/interest/deleteimage?incode=1
    @RequestMapping(value = "/deleteimage", method = { RequestMethod.DELETE }, consumes = {
            MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> deleteImagePost(
            @RequestHeader(name = "token") String token,
            @RequestParam(name = "incode") long incode) {

        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);

        try {
            String userid = jwtUtil.extractUsername(token);
            System.out.println("userid =>" + userid);

            int ret = intService1.deleteInterestImage(incode);
            if (ret == 1) {
                map.put("status", 200);
            }

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

}
