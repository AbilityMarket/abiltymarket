package com.example.restcontroller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.entity.AbTipEntity;
import com.example.entity.AbTipImageEntity;
import com.example.entity.AbTipImageEntityProjection;
import com.example.jwt.JwtUtil;
import com.example.service.AbTipImageService3;

import org.springframework.beans.factory.annotation.Autowired;
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
        @RequestParam(name = "abtno") Long abtno,
        @RequestParam(name = "file") MultipartFile[] file) {

        Map<String, Object> map = new HashMap<>();

        try {
            //토큰 필요함(토큰 추출)
            String userid = jwtUtil.extractUsername(token);
            System.out.println("RequestMapping username : " + userid);

            List<AbTipImageEntity> list = new ArrayList<>();

            for(int i=0; i<file.length; i++) {
                if(file != null) {
                    AbTipImageEntity abTipImage = new AbTipImageEntity();
                    abTipImage.setAbimage(file[i].getBytes());
                    abTipImage.setAbimagename(file[i].getOriginalFilename());
                    abTipImage.setAbimagesize(file[i].getSize());
                    abTipImage.setAbimagetype(file[i].getContentType());
                    
                    AbTipEntity abt = new AbTipEntity();
                    abt.setAbtno(abtno);
                    abTipImage.setAbtip(abt);
                    // System.out.println(abt.toString());
                    
                    list.add(abTipImage);
                    // System.out.println(list);   
                }
            }
            int ret = abtiService3.insertAbTipImage(list);
            if(ret == 1) {
                map.put("result", "등록완료!");
                map.put("status", 200);
            }
            else{
                map.put("status", 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // 팁 이미지 삭제
    // 127.0.0.1:9090/ROOT/api/abtipimg/delete
    @RequestMapping(value = {"/delete"},
        method = {RequestMethod.DELETE},
        consumes = {MediaType.ALL_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public Map<String, Object> deleteDELETE(
        @RequestHeader(name = "token") String token,
        @RequestParam(name = "abino") Long abino) {

        Map<String, Object> map = new HashMap<>();

        try {
            //토큰 필요함(토큰 추출)
            String username = jwtUtil.extractUsername(token);
            // System.out.println("RequestMapping username : " + username);
            
            if(username != null) {
                int ret = abtiService3.deleteAbTipImage(username, abino);
                if(ret == 1) {
                    map.put("result", "삭제완료!");
                    map.put("status", 200);
                }
            }
            else {
                map.put("result", "작성자X");
                map.put("status", 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // 팁 이미지 부분 삭제
    // 127.0.0.1:9090/ROOT/api/abtipimg/deletelist
    @RequestMapping(value = {"/deletelist"},
        method = {RequestMethod.DELETE},
        consumes = {MediaType.ALL_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public Map<String, Object> deleteListDELETE(
        @RequestHeader(name = "token") String token,
        @RequestParam(name = "abino") Long[] abino) {

        Map<String, Object> map = new HashMap<>();

        try {
            //토큰 필요함(토큰 추출)
            String username = jwtUtil.extractUsername(token);
            // System.out.println("RequestMapping username : " + username);

            if(username != null) {
                int ret = abtiService3.deleteAbTipImgPart(abino);
                if(ret == 1) {
                    map.put("status", 200);
                }
                else {
                    map.put("status", 0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // 팁 이미지 가져오기 (파일전체)
    // 127.0.0.1:9090/ROOT/api/abtipimg/selectone
    @RequestMapping(value = {"/selectone"},
        method = {RequestMethod.GET},
        consumes = {MediaType.ALL_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public Map<String, Object> selectoneGET(
        @RequestHeader(name = "token") String token,
        @RequestParam(name = "abino") Long abino) {

        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);

        try {
            //토큰 필요함(토큰 추출)
            String userid = jwtUtil.extractUsername(token);
            System.out.println("RequestMapping username : " + userid);

            AbTipImageEntity abtie = abtiService3.selectOneAbTipImage(abino);
            if(abtie != null) {
                map.put("status", 200);
                map.put("abtie", abtie);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // 팁 이미지 가져오기(url 주소생성)
    // 127.0.0.1:9090/ROOT/api/abtipimg/image?abino=
    @GetMapping(value = {"/image"})
    public ResponseEntity<byte[]> imageGET(
        @RequestParam(name = "abino") Long abino) {

        // 이미지명, 이미지크기, 이미지종류, 이미지데이터
        AbTipImageEntity abtimg = abtiService3.selectOneAbTipImage(abino);
        // System.out.println(abtimg.getAbimagename());

        // 첨부한 파일(이미지) 존재
        if(abtimg.getAbimagesize() > 0) {

            HttpHeaders headers = new HttpHeaders();

            if (abtimg.getAbimagetype().equals("image/jpeg")) {
                headers.setContentType(MediaType.IMAGE_JPEG);
            } else if (abtimg.getAbimagetype().equals("image/png")) {
                headers.setContentType(MediaType.IMAGE_PNG);
            } else if (abtimg.getAbimagetype().equals("image/gif")) {
                headers.setContentType(MediaType.IMAGE_GIF);
            }

            // 이미지 byte[], headers, HttpStatus.Ok
            ResponseEntity<byte[]> response = new ResponseEntity<>(abtimg.getAbimage(),headers, HttpStatus.OK);
            return response;
        }
        // 첨부한 파일(이미지) 없을 때
        else {
            return null;
        }
    }

    // 팁 게시판 번호에 해당하는 이미지 조회 (url버전)
    // 127.0.0.1:9090/ROOT/api/abtipimg/selectimgs?abino=
    @RequestMapping(value = {"/selectimgs"},
        method = { RequestMethod.GET },
        consumes = {MediaType.ALL_VALUE },
        produces = { MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> selectOneGET(
        @RequestParam(name = "abtno") long abtno) {
        Map<String, Object> map = new HashMap<>();
        // System.out.println(abtno);
        map.put("status", 0);
        try {
            List<AbTipImageEntityProjection> list = abtiService3.selectAbtipImgProjection(abtno);
            if(list != null) {
                // url 생성
                List<String> urlList = new ArrayList<String>();
                for(AbTipImageEntityProjection obj : list) {
                    urlList.add("/ROOT/api/abtipimg/image?abino=" + obj.getAbino());
                }
                map.put("status", 200);
                map.put("list", urlList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // 팁 이미지 1개 수정
    // 127.0.0.1:9090/ROOT/api/abtipimg/updateone
    @RequestMapping(value = {"/updateone"},
        method = {RequestMethod.PUT},
        consumes = {MediaType.ALL_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public Map<String, Object> updatePUT(
        @RequestHeader(name = "token") String token,
        @ModelAttribute AbTipImageEntity abtie,
        @RequestParam(name = "file") MultipartFile file) {

        Map<String, Object> map = new HashMap<>();

        //System.out.println("abtie==="+abtie.toString());

        try {
            //토큰 필요함(토큰 추출)
            String userid = jwtUtil.extractUsername(token);
            System.out.println("RequestMapping username : " + userid);

            // 기존 이미지 불러오기
            //AbTipImageService3.selectOneAbTipImage(long abino) : AbTipImageEntity
            AbTipImageEntity abtie1 = abtiService3.selectOneAbTipImage(abtie.getAbino());
            System.out.println("기존abtie1==="+abtie1.getAbimagename());

            // 이미지 수정 하기
            if(!file.isEmpty()) {
                abtie1.setAbimage(file.getBytes());
                abtie1.setAbimagename(file.getOriginalFilename());
                abtie1.setAbimagesize(file.getSize());
                abtie1.setAbimagetype(file.getContentType());
            }
            // System.out.println("새로운file==="+file.getOriginalFilename());

            //AbTipImageService3.updateAbTipImage(AbTipImageEntity abtipimg) : int
            int ret =  abtiService3.updateAbTipImage(abtie1);
            if(ret == 1) {
                map.put("status", 200);
            }
            else{
                map.put("status", 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // 팁 이미지 일괄 수정
    // 127.0.0.1:9090/ROOT/api/abtipimg/updatelist
    @RequestMapping(value = {"/updatelist"},
        method = {RequestMethod.PUT},
        consumes = {MediaType.ALL_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public Map<String, Object> updateListPUT(
        @RequestHeader(name = "token") String token,
        @RequestParam(name = "file", required = false) MultipartFile file[],
        @RequestParam(name = "abino") Long[] abino) {

        Map<String, Object> map = new HashMap<>();

        try {
            //토큰 필요함(토큰 추출)
            String userid = jwtUtil.extractUsername(token);
            System.out.println("RequestMapping username : " + userid);

            List<AbTipImageEntity> list = new ArrayList<>();
            // System.out.println("list==="+list);
            for (int i=0; i<file.length; i++) {
                if (file != null) {
                    if (!file[i].isEmpty()) {
                        AbTipImageEntity abtimg = abtiService3.selectOneAbTipImage(abino[i]);
                        // System.out.println("기존==="+abtimg.getAbimagename()); //기존파일

                        abtimg.setAbimage(file[i].getBytes());
                        abtimg.setAbimagename(file[i].getOriginalFilename());
                        abtimg.setAbimagesize(file[i].getSize());
                        abtimg.setAbimagetype(file[i].getContentType());;

                        list.add(abtimg);
                        // System.out.println("새로운==="+abtimg.getAbimagename());
                    }    
                }
            }
            int ret = abtiService3.updateAbTipImgList(list);
            if(ret == 1) {
                map.put("status", 200);
                map.put("result", "일괄수정성공!");
            }
            else {
                map.put("status", 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }
    
}
