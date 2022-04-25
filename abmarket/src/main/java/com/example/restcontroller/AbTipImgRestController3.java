package com.example.restcontroller;

import java.util.HashMap;
import java.util.List;
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
    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    AbTipImageService3 abtiService3;

    // 팁 사진 등록
    // 127.0.0.1:9090/ROOT/api/abtipimg/insert
    @RequestMapping(value = {"/insert"},
        method = {RequestMethod.POST},
        consumes = {MediaType.ALL_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public Map<String, Object> insertPOST(
        @ModelAttribute AbTipEntity abTip,
        @ModelAttribute AbTipImageEntity abTipImage,
        @RequestParam(name = "file") MultipartFile file) {

        Map<String, Object> map = new HashMap<>();
        
        map.put("status", 0);

        try {

            // 이미지 첨부
            abTipImage.setAbimage(file.getBytes());
            abTipImage.setAbimagename(file.getOriginalFilename());
            abTipImage.setAbimagesize(file.getSize());
            abTipImage.setAbimagetype(file.getContentType());

            System.out.println(abTipImage.getAbimagesize());

            //AbTipImageService3.insertAbTipImage(List<AbTipImageEntity> list) : int
            //int ret = abtiService3.insertAbTipImage(list);

            map.put("status", 200);
            
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
            
    }
}
