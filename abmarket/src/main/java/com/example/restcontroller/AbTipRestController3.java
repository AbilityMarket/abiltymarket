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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
                        
        try {
            //토큰 필요함(토큰 추출)
            String userid = jwtUtil.extractUsername(token);
            System.out.println("RequestMapping username : " + userid);

            MemberEntity memberEntity = new MemberEntity();
            memberEntity.setUid(userid);
            System.out.println(memberEntity);
            
            abTip.setMember(memberEntity);
            System.out.println(abTip.toString());
            
            //AbTipService3.insertAbTip(AbTipEntity abtip) : int
            int ret = abtService3.insertAbTip(abTip);
            if(ret == 1) {
                map.put("status", 200);
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

            int ret = abtService3.deleteOneAbTip(abtno);
            if(ret == 1) {
                map.put("status", 200);
            }

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // 팁 전체 목록 조회(토큰X, 페이지네이션, 검색)
    // 127.0.0.1:9090/ROOT/api/abtip/selectlist
    // abttitle(제목) 검색 설정함
    @RequestMapping(value = {"/selectlist"},
        method = {RequestMethod.GET},
        consumes = {MediaType.ALL_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public Map<String, Object> selectListGET(
        @RequestParam(value = "title", defaultValue = "") String abttitle,
        @RequestParam(value = "page", defaultValue = "0") int page) {

        Map<String, Object> map = new HashMap<>();

        // 팁 전체 목록 페이지 1로 가도록 강제적으로 설정해놓기
        // if (page == 0) {
            //     return ;
        // }

        // 페이지디폴트 0으로 설정함
        Pageable pageable = PageRequest.of(page-1, 10);
            
        try {
            List<AbTipEntity> list = abtService3.selectListAbTip(pageable, abttitle);

            if(list != null) {
                map.put("title", abttitle);
                map.put("page", page);
                // map.put("start", (page-1)*10+1);
                // map.put("end", page*10);

                map.put("status", 200);
                map.put("result", list);
                //System.out.println(list.toString());
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

    // 팁 1개 조회
    // 127.0.0.1:9090/ROOT/api/abtip/selectone
    @RequestMapping(value = {"/selectone"},
        method = {RequestMethod.GET},
        consumes = {MediaType.ALL_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public Map<String, Object> selectOneGET(@RequestParam(name = "abtno") long abtno) {
        
        Map<String, Object> map = new HashMap<>();

        try {
            AbTipEntity result = abtService3.selectOneAbTip(abtno);
            //List<Long> abtimg = abtiService3.selectAbtipImgList(abtno);
            if(result != null) {
                map.put("result", result);
                //map.put("abtimg", abtimg);
                map.put("status", 200);
            } else{
                map.put("status", 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // 팁 1개 수정
    // 127.0.0.1:9090/ROOT/api/abtip/updateone
    @RequestMapping(value = {"/updateone"},
        method = {RequestMethod.PUT},
        consumes = {MediaType.ALL_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public Map<String, Object> updateOnePUT(
        @RequestHeader(name = "token") String token,
        @ModelAttribute AbTipEntity abtip,
        @RequestParam(name = "abtno") long abtno) {

        Map<String, Object> map = new HashMap<>();

        try {
            //토큰 필요함(토큰 추출)
            String userid = jwtUtil.extractUsername(token);
            System.out.println("RequestMapping username : " + userid);

            //AbTipService3.updateOneAbTip(AbTipEntity abtip) : AbTipEntity
            AbTipEntity abt1 = abtService3.updateOneAbTip(abtip);

            //정보 변경
            abt1.setAbttitle(abtip.getAbttitle());
            abt1.setAbtcontent(abtip.getAbtcontent());
            //abt1.setAbimageList(abt.getAbimageList());
            
            //변경 후 저장
            abtService3.updateOneAbTip(abtip);
            map.put("status", 200);

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

}
