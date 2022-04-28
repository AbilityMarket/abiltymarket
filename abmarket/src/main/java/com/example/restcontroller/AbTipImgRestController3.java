package com.example.restcontroller;

import java.util.HashMap;
import java.util.Map;

import com.example.entity.AbTipEntity;
import com.example.entity.AbTipImageEntity;
import com.example.jwt.JwtUtil;
import com.example.service.AbTipImageService3;

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
@RequestMapping(value = "/api/abtipimg")

public class AbTipImgRestController3 {

    // 토큰
    @Autowired JwtUtil jwtUtil;

    @Autowired
    AbTipImageService3 abtiService3;

    // 팁 사진 등록 (토큰 필요)
    // 127.0.0.1:9090/ROOT/api/abtipimg/insert
    @RequestMapping(value = {"/insert"},
        method = {RequestMethod.POST},
        consumes = {MediaType.ALL_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public Map<String, Object> insertPOST(
        @RequestHeader(name = "token") String token,
        @ModelAttribute AbTipImageEntity abtimg,
        @RequestParam(name = "abtno") Long abtno,
        //@RequestParam(name = "file") MultipartFile[] file
        @RequestParam(name = "file") MultipartFile file) {


        Map<String, Object> map = new HashMap<>();

        try {
            //토큰 필요함(토큰 추출)
            String userid = jwtUtil.extractUsername(token);
            System.out.println("RequestMapping username : " + userid);

            //List<AbTipImageEntity> list = new ArrayList<>();
            // for(int i=0; i<file.length; i++) {
            //     if(file != null) {
            //         AbTipImageEntity abTipImage = new AbTipImageEntity();
            //         abTipImage.setAbimage(file[i].getBytes());
            //         abTipImage.setAbimagename(file[i].getOriginalFilename());
            //         abTipImage.setAbimagesize(file[i].getSize());
            //         abTipImage.setAbimagetype(file[i].getContentType());
            //         list.add(abTipImage);
            //         abtiService3.insertAbTipImage(list);
            //         System.out.println("abTipImage===="+abTipImage.getAbino());
            //         AbTipEntity abt = new AbTipEntity();
            //         abt.setAbtno(abtno);
            //         System.out.println(abt.toString());
            //         abtimg.setAbtip(abt);
            //         System.out.println(abtimg.toString());
            //         int ret = abtiService3.insertAbTipImage(list);
            //         if(ret == 1) {
            //             map.put("status", 200);
            //         }
            //         else{
            //             map.put("status", 0);
            //         }
            //     }
            // }
            if(file != null) {
                
                abtimg.setAbimage(file.getBytes());
                abtimg.setAbimagename(file.getOriginalFilename());
                abtimg.setAbimagesize(file.getSize());
                abtimg.setAbimagetype(file.getContentType());

                AbTipEntity abt = new AbTipEntity();
                abt.setAbtno(abtno);
                System.out.println(abt.toString());

                abtimg.setAbtip(abt);
                System.out.println(abtimg.getAbimagename());

                int ret = abtiService3.insertAbTipImage(abtimg);
                if(ret == 1) {
                    map.put("status", 200);
                }
                else{
                    map.put("status", 0);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // 팁 이미지 삭제(동일인물인지 확인해야됨)
    // 127.0.0.1:9090/ROOT/api/abtipimg/delete
    @RequestMapping(value = {"/delete"},
        method = {RequestMethod.DELETE},
        consumes = {MediaType.ALL_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public Map<String, Object> deleteDELETE(
        @RequestHeader(name = "token") String token,
        @RequestParam(name = "abino") long abino) {

        Map<String, Object> map = new HashMap<>();

        map.put("status", 0);

        try {
            //토큰 필요함(토큰 추출)
            String username = jwtUtil.extractUsername(token);
            System.out.println("RequestMapping username : " + username);

            int ret = abtiService3.deleteAbTipImage(username, abino);
            if(ret == 1) {
                map.put("status", 200);
            }

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // 팁 이미지 수정
    // 127.0.0.1:9090/ROOT/api/abtipimg/delete
    @RequestMapping(value = {"/update"},
        method = {RequestMethod.PUT},
        consumes = {MediaType.ALL_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public Map<String, Object> updatePUT(
        @RequestHeader(name = "token") String token) {

        Map<String, Object> map = new HashMap<>();

        try {
            //토큰 필요함(토큰 추출)
            String userid = jwtUtil.extractUsername(token);
            System.out.println("RequestMapping username : " + userid);

            // 기존 이미지 불러오기
            

            map.put("status", 200);
            
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;


    }
    
}
