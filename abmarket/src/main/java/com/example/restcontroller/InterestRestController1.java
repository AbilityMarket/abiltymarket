package com.example.restcontroller;

import java.util.HashMap;
import java.util.Map;

import com.example.entity.InterestEntity;
import com.example.entity.MemberEntity;
import com.example.entity.MeminterestEntity;
import com.example.jwt.JwtUtil;
import com.example.repository.MemberRespository2;
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

    @Autowired
    MemberRespository2 memRepository2;

    // 관심사 등록하기(관리자)
    // 127.0.0.1:9090/ROOT/api/interest/insertInterest
    @RequestMapping(value = "/insertInterest", method = { RequestMethod.POST }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> insertInterestPost(
            @ModelAttribute InterestEntity interest,
            @RequestHeader(name = "token") String token,
            @RequestParam(name = "file", required = false) MultipartFile file) {

        Map<String, Object> map = new HashMap<>();

        try {
            String admin = jwtUtil.extractUsername(token);
            System.out.println("adminId =>" + admin);
            MemberEntity member = memRepository2.getById(admin);

            if (file != null) {
                if (!file.isEmpty()) {
                    interest.setInimage(file.getBytes());
                    interest.setInimagename(file.getOriginalFilename());
                    interest.setInimagesize(file.getSize());
                    interest.setInimagetype(file.getContentType());
                }
            }
            interest.setInname(interest.getInname());
            interest.setIncategory(interest.getIncategory());

            if (member.getUrole().equals("ADMIN")) {
                int ret = intService1.insertInterest(interest);
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

    // 관심사 조회하기(관리자)
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

    // 관심사 수정하기(관리자)
    // 127.0.0.1:9090/ROOT/api/interest/update
    @RequestMapping(value = "/update", method = { RequestMethod.PUT }, consumes = { MediaType.ALL_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> updatePost(
            @RequestHeader(name = "token") String token,
            @RequestParam(name = "file", required = false) MultipartFile file,
            @ModelAttribute InterestEntity interest) {

        Map<String, Object> map = new HashMap<>();
        System.out.println("TOKEN =>" + token);
        System.out.println("intEntity :" + interest.toString());

        try {
            String admin = jwtUtil.extractUsername(token);
            System.out.println("adminId =>" + admin);
            MemberEntity member = memRepository2.getById(admin);

            // 관리자 권한
            if (member.getUrole().equals("ADMIN")) {
                InterestEntity interest1 = intService1.selectOneInterest(interest.getIncode());
                interest1.setInimage(file.getBytes());
                interest1.setInimagename(file.getOriginalFilename());
                interest1.setInimagesize(file.getSize());
                interest1.setInimagetype(file.getContentType());

                interest1.setInname(interest.getInname());
                interest1.setIncategory(interest.getIncategory());

                int ret = intService1.updateInterest(interest1);
                if (ret == 1) {
                    map.put("status", 200);
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

    // 관심사 삭제하기(관리자)
    // 127.0.0.1:9090/ROOT/api/interest/delete?incode=1
    @RequestMapping(value = "/delete", method = { RequestMethod.DELETE }, consumes = {
            MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> deletePost(
            @RequestHeader(name = "token") String token,
            @RequestParam(name = "incode") long incode) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);

        try {
            String admin = jwtUtil.extractUsername(token);
            System.out.println("adminId =>" + admin);
            MemberEntity member = memRepository2.getById(admin);

            // 관리자 권한
            if (member.getUrole().equals("ADMIN")) {
                int ret = intService1.deleteInterest(incode);
                if (ret == 1) {
                    map.put("status", 200);
                } else {
                    map.put("status", 0);
                }
            } else {
                map.put("status", 0);
                map.put("result", "관리자권한");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("value", -1);
        }
        return map;
    }

}
