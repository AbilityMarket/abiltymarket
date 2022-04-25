package com.example.restcontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.entity.AbTipEntity;
import com.example.entity.AbTipImageEntity;
import com.example.entity.MemberEntity;
import com.example.jwt.JwtUtil;
import com.example.service.AbTipImageService3;
import com.example.service.AbTipService3;

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
@RequestMapping(value = "/api/abtip")

public class AbTipRestController3 {
    
    // 토큰
    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    AbTipService3 abtService3;

    @Autowired
    AbTipImageService3 abtiService3;

    // 팁 등록 (토큰 필요)
    // 127.0.0.1:9090/ROOT/api/abtip/insert
    @RequestMapping(value = {"/insert"},
        method = {RequestMethod.POST},
        consumes = {MediaType.ALL_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public Map<String, Object> insertPOST(
        @RequestHeader(name = "token") String token,
        @ModelAttribute AbTipEntity abTip,
        @ModelAttribute AbTipImageEntity abTipImage,
        @RequestParam(name = "file") MultipartFile file) {
    
        Map<String, Object> map = new HashMap<>();
            
        map.put("status", 0);
            
            
        try {
            //토큰 필요함(토큰 추출)
            String userid = jwtUtil.extractUsername(token);
            System.out.println("RequestMapping username : " + userid);
            System.out.println(abTip.toString());
            
            MemberEntity memberEntity = new MemberEntity();
            memberEntity.setUid(userid);
            //System.out.println(memberEntity.getUid());

            abTip.setMember(memberEntity);

            // 이미지 첨부 -> abTipImageEntity에 등록
            // abTipImage.setAbimage(file.getBytes());
            // abTipImage.setAbimagename(file.getOriginalFilename());
            // abTipImage.setAbimagesize(file.getSize());
            // abTipImage.setAbimagetype(file.getContentType());

            AbTipImageEntity abTipImgEntity = new AbTipImageEntity();
            System.out.println(abTipImgEntity);

            map.put("status", 200);

            
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // 팁 1개 삭제
    // 127.0.0.1:9090/ROOT/api/abtip/deleteone
    @RequestMapping(value = {"/deleteone"},
        method = {RequestMethod.DELETE},
        consumes = {MediaType.ALL_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public Map<String, Object> deletePOST(
        @RequestHeader(name = "token") String token,
        @RequestParam(name = "abtno") long abtno) {

        Map<String, Object> map = new HashMap<>();

        map.put("status", 0);

        try {
            //토큰 필요함(토큰 추출)
            String username = jwtUtil.extractUsername(token);
            System.out.println("RequestMapping username : " + username);

            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // 팁 전체 목록 조회
    @RequestMapping(value = {"/selectlist"},
        method = {RequestMethod.GET},
        consumes = {MediaType.ALL_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public Map<String, Object> selectListGET() {

        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);

        try {
            List<AbTipEntity> list = abtService3.selectListAbTip(map);
            //System.out.println();

            map.put("result", list);
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

}
