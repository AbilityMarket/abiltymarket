package com.example.restcontroller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.entity.ReviewEntity;
import com.example.entity.ReviewImageEntity;
import com.example.entity.ReviewImageEntityProjection;
import com.example.jwt.JwtUtil;
import com.example.repository.ReviewRepository1;
import com.example.service.ReviewImageService1;

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
@RequestMapping(value = "/api/reviewimage")
public class ReviewImageRestController1 {

    @Autowired
    ReviewImageService1 RevImgService1;

    @Autowired
    ReviewRepository1 ReviewRepository1;

    @Autowired
    JwtUtil jwtUtil;

    // 이미지 없을 때 처리
    @Autowired
    ResourceLoader resLoader;

    // 후기 이미지 등록
    // 127.0.0.1:9090/ROOT/api/reviewimage/insert?revno=1
    @RequestMapping(value = "/insert", method = { RequestMethod.POST }, consumes = { MediaType.ALL_VALUE }, produces = {
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
                reviewImage.setRvimagename(file.getOriginalFilename());
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

    // 후기 이미지 등록(여러개)
    // 127.0.0.1:9090/ROOT/api/reviewimage/insertbatch
    @RequestMapping(value = "/insertbatch", method = { RequestMethod.POST }, consumes = {
            MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> insertbatchPost(
            @RequestHeader(name = "token") String token,
            @RequestParam(name = "file", required = false) MultipartFile file[],
            @RequestParam(name = "revno") Long revno) {

        Map<String, Object> map = new HashMap<>();

        try {
            String userid = jwtUtil.extractUsername(token);
            System.out.println(userid);

            // 리뷰 작성자와 같을 시 이미지를 넣는다.
            // ReviewEntity review = ReviewRepository1.getById(revno);
            // if (userid.equals(review.getRevno())) {

            List<ReviewImageEntity> list = new ArrayList<>();
            for (int i = 0; i < file.length; i++) {
                if (file != null) {
                    if (!file[i].isEmpty()) {
                        ReviewImageEntity reviewImage = new ReviewImageEntity();
                        reviewImage.setRvimage(file[i].getBytes());
                        reviewImage.setRvimagename(file[i].getOriginalFilename());
                        reviewImage.setRvimagesize(file[i].getSize());
                        reviewImage.setRvimagetype(file[i].getContentType());

                        ReviewEntity review1 = new ReviewEntity();
                        review1.setRevno(revno);
                        System.out.println(review1.toString());
                        reviewImage.setReview(review1);

                        list.add(reviewImage);
                    }
                }
            }
            int ret = RevImgService1.insertReviewImageBatch(list);
            if (ret == 1) {
                map.put("result", "등록완료");
                map.put("status", 200);
            } else {
                map.put("status", 0);
            }
            // }

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // 후기 이미지 가져오기(여러개)
    // 127.0.0.1:9090/ROOT/api/reviewimage/image
    @GetMapping(value = "/image") // url 주소생성
    public ResponseEntity<byte[]> imageGET(
            @RequestParam(name = "rvimno") long rvimno)
            throws IOException {

        // 이미지명, 이미지크기, 이미지종류, 이미지데이터
        ReviewImageEntity reviewImage = RevImgService1.selectReviewImage(rvimno);
        System.out.println(reviewImage.getRvimagename());

        // 이미지가 있을때
        if (reviewImage.getRvimagesize() > 0) { // 첨부한 파일 존재
            HttpHeaders headers = new HttpHeaders();

            if (reviewImage.getRvimagetype().equals("image/jpeg")) {
                headers.setContentType(MediaType.IMAGE_JPEG);
            } else if (reviewImage.getRvimagetype().equals("image/png")) {
                headers.setContentType(MediaType.IMAGE_PNG);
            } else if (reviewImage.getRvimagetype().equals("image/gif")) {
                headers.setContentType(MediaType.IMAGE_GIF);
            }

            // 이미지 byte[], headers, HttpStatus.Ok
            ResponseEntity<byte[]> response = new ResponseEntity<>(reviewImage.getRvimage(),
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

    // 이미지 호출하기
    // 127.0.0.1:9090/ROOT/api/reviewimage/selectlist
    @RequestMapping(value = "/selectlist", method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> selectOneGET(@RequestParam(name = "revno") long revno) {
        Map<String, Object> map = new HashMap<>();
        System.out.println(revno);
        map.put("status", 0);

        try {
            List<ReviewImageEntityProjection> list = RevImgService1.selectReviewImageProjection(revno);
            if (list != null) {
                List<String> list1 = new ArrayList<String>();

                for (ReviewImageEntityProjection obj : list) {
                    list1.add("/ROOT/api/reviewimage/image?rvimno=" + obj.getrvimno());
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

    // 후기 이미지 수정하기(일괄)
    // 127.0.0.1:9090/ROOT/api/reviewimage/updatebatch
    // @RequestMapping(value = "/updatebatch", method = { RequestMethod.PUT },
    // consumes = {
    // MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    // public Map<String, Object> updatebatchPost(
    // @RequestHeader(name = "token") String token,
    // @RequestParam(name = "file", required = false) MultipartFile file[],
    // @RequestParam(name = "revno") Long revno,
    // @RequestParam(name = "rvimno") Long[] rvimno) {

    // Map<String, Object> map = new HashMap<>();

    // try {
    // String userid = jwtUtil.extractUsername(token);
    // System.out.println(userid);

    // List<ReviewImageEntity> list = new ArrayList<>();
    // for (int i = 0; i < file.length; i++) {
    // if (file != null) {
    // if (!file[i].isEmpty()) {
    // ReviewImageEntity reviewImage = RevImgService1.select
    // reviewImage.setRvimage(file[i].getBytes());
    // reviewImage.setRvimagename(file[i].getOriginalFilename());
    // reviewImage.setRvimagesize(file[i].getSize());
    // reviewImage.setRvimagetype(file[i].getContentType());

    // ReviewEntity review1 = new ReviewEntity();
    // review1.setRevno(revno);
    // System.out.println(review1.toString());
    // reviewImage.setReview(review1);

    // list.add(reviewImage);
    // }
    // }
    // }
    // int ret = RevImgService1.insertReviewImageBatch(list);
    // if (ret == 1) {
    // map.put("result", "일괄수정완료");
    // map.put("status", 200);
    // } else {
    // map.put("status", 0);
    // }

    // } catch (Exception e) {
    // e.printStackTrace();
    // map.put("status", -1);
    // }
    // return map;
    // }

    // 후기 이미지 삭제하기(부분)

}
