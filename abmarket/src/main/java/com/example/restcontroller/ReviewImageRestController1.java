package com.example.restcontroller;

import java.util.HashMap;
import java.util.Map;

import com.example.entity.ReviewEntity;
import com.example.entity.ReviewImageEntity;
import com.example.jwt.JwtUtil;
import com.example.service.ReviewImageService1;

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
@RequestMapping(value = "/api/reviewimage")
public class ReviewImageRestController1 {

    @Autowired
    ReviewImageService1 RevImgService1;

    @Autowired
    JwtUtil jwtUtil;

    // 후기 이미지 등록
    // 127.0.0.1:9090/ROOT/api/reviewimage/insert?revno=1
    @RequestMapping(value = "insert", method = { RequestMethod.POST }, consumes = { MediaType.ALL_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> insertPost(
            @RequestHeader(name = "token") String token,
            @ModelAttribute ReviewImageEntity reviewImage,
            @RequestParam(name = "file") MultipartFile file,
            @RequestParam(name = "revno") Long revno) {

        Map<String, Object> map = new HashMap<>();

        try {
            String userid = jwtUtil.extractUsername(token);
            System.out.println("userid =>" + userid);

            if (file != null) {
                reviewImage.setRvimage(file.getBytes());
                reviewImage.setRvimagename(file.getName());
                reviewImage.setRvimagesize(file.getSize());
                reviewImage.setRvimagetype(file.getContentType());
            }

            ReviewEntity RevEntity = new ReviewEntity();
            RevEntity.setRevno(revno);
            System.out.println(RevEntity);
            reviewImage.setReview(RevEntity);

            int ret = RevImgService1.insertReviewImage(reviewImage);
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

    // 후기 이미지 조회
    // 127.0.0.1:9090/ROOT/api/reviewimage/select?rvimno=1
    @RequestMapping(value = "/select", method = { RequestMethod.GET }, consumes = { MediaType.ALL_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> selectGET(
            @RequestParam(name = "rvimno") long rvimno) {
        Map<String, Object> map = new HashMap<>();

        try {
            ReviewImageEntity retRvim = RevImgService1.selectReviewImageOne(rvimno);
            if (retRvim != null) {
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

    // 후기 이미지 수정
    // 127.0.0.1:9090/ROOT/api/reviewimage/update?revno=1
    @RequestMapping(value = "update", method = { RequestMethod.PUT }, consumes = { MediaType.ALL_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> updatePost(
            @RequestHeader(name = "token") String token,
            @ModelAttribute ReviewImageEntity reviewImage,
            @RequestParam(name = "file") MultipartFile file) {

        Map<String, Object> map = new HashMap<>();

        try {
            String userid = jwtUtil.extractUsername(token);
            System.out.println("userid =>" + userid);

            if (file != null) {
                ReviewImageEntity reviewImage1 = RevImgService1.selectReviewImageOne(reviewImage.getRvimno());
                reviewImage1.setRvimage(file.getBytes());
                reviewImage1.setRvimagename(file.getName());
                reviewImage1.setRvimagesize(file.getSize());
                reviewImage1.setRvimagetype(file.getContentType());

                int ret = RevImgService1.updateReviewImage(reviewImage1);
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

    // 후기 이미지 삭제
    // 127.0.0.1:9090/ROOT/api/reviewimage/delete?rvimno=1
    @RequestMapping(value = "/delete", method = { RequestMethod.DELETE }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> deletePost(
            @RequestHeader(name = "token") String token,
            @RequestParam(name = "rvimno") long rvimno) {

        Map<String, Object> map = new HashMap<>();

        try {
            String userid = jwtUtil.extractUsername(token);
            System.out.println("userid =>" + userid);

            int ret = RevImgService1.deleteReviewImage(rvimno);
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

    // 후기 이미지 가져오기(여러개)

}
