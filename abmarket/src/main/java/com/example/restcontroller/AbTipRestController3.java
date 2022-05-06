package com.example.restcontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.entity.AbTipEntity;
import com.example.entity.AbTipImageEntity;
import com.example.entity.MemberEntity;
import com.example.jwt.JwtUtil;
import com.example.repository.AbTipRepository3;
import com.example.service.AbTipImageService3;
import com.example.service.AbTipService3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/abtip")

public class AbTipRestController3 {
    
    // 토큰
    @Autowired JwtUtil jwtUtil;

    @Autowired
    AbTipService3 abtService3;

    @Autowired
    AbTipImageService3 abtiService3;

    @Autowired
    AbTipRepository3 abtRepository3;


    // 팁 등록 (토큰 필요)
    // 127.0.0.1:9090/ROOT/api/abtip/insert
    @RequestMapping(value = {"/insert"},
        method = {RequestMethod.POST},
        consumes = {MediaType.ALL_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public Map<String, Object> insertPOST(
        @RequestHeader(name = "token") String token,
        @RequestBody AbTipEntity abTip) {
    
        Map<String, Object> map = new HashMap<>();
                        
        try {
            //토큰 필요함(토큰 추출)
            String userid = jwtUtil.extractUsername(token);
            System.out.println("RequestMapping username : " + userid);

            MemberEntity memberEntity = new MemberEntity();
            memberEntity.setUid(userid);
            //System.out.println(memberEntity);
            
            abTip.setMember(memberEntity);
            //System.out.println(abTip.toString());
            
            //AbTipService3.insertAbTip(AbTipEntity abtip) : int
            int ret = abtService3.insertAbTip(abTip);
            if(ret == 1) {
                map.put("result", "등록완료!");
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

        try {
            //토큰 필요함(토큰 추출)
            String userid = jwtUtil.extractUsername(token);
            System.out.println("RequestMapping username : " + userid);

            //게시판 글번호 추출
            AbTipEntity abte = abtRepository3.getById(abtno);

            //토큰(작성자)과 글번호가 동일한지 검사
            if(userid.equals(abte.getMember().getUid())) {

                //연결 된 이미지도 함께 삭제 하도록 설정(다시)

                int ret = abtService3.deleteOneAbTip(abtno);
                System.out.println(ret);
                if(ret == 1) {
                    map.put("result", "삭제완료!");
                    map.put("status", 200);
                }
            }
            else {
                map.put("result", "작성자X!");
                map.put("status", 0);
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
                //AbTipService3.selectCountAbTip(Map<String,Object> map) : long
                long total = abtService3.selectCountAbTip(map);

                map.put("title", abttitle);
                map.put("page", page);
                map.put("total", total);
                // map.put("start", (page-1)*10+1);
                // map.put("end", page*10);

                map.put("status", 200);
                map.put("result", list);
                //System.out.println(list.toString());
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

    // 팁 1개 조회 (팁 이미지 가져오기)
    // 127.0.0.1:9090/ROOT/api/abtip/selectone
    @RequestMapping(value = {"/selectone"},
        method = {RequestMethod.GET},
        consumes = {MediaType.ALL_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public Map<String, Object> selectOneGET(
        @RequestHeader(name = "token") String token,
        @RequestParam(name = "code") long code) {
        
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);

        try {
            //토큰 필요함(토큰 추출)
            String username = jwtUtil.extractUsername(token);
            System.out.println("RequestMapping username : " + username);

            AbTipEntity result = abtService3.selectOneAbTip(code);
            //AbTipImageService3.selectAbTipImage(long abino) : List<AbTipImageEntity>
            List<AbTipImageEntity> list = abtiService3.selectAbTipImage(result.getAbtno());

            if(result != null) {
                map.put("result", result);
                map.put("list", list);
                map.put("status", 200);
            }
            // else {
            //     map.put("status", 0);
            // }
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

        //System.out.println("abtip=====" + abtip.toString());

        try {
            //토큰 필요함(토큰 추출)
            String userid = jwtUtil.extractUsername(token);
            System.out.println("RequestMapping username : " + userid);

            //게시판 글번호 추출
            AbTipEntity abte = abtRepository3.getById(abtno);
            //List<AbTipImageEntity> list = abtiService3.selectAbTipImage(abte.getAbtno());
            // map.put("status", 200);
            // map.put("list", list);

            //토큰(작성자)과 글번호가 동일한지 검사
            if(userid.equals(abte.getMember().getUid())) {
                AbTipEntity result = abtService3.selectOneAbTip(abtip.getAbtno());
                System.out.println("result===="+result);

                //정보 변경 (제목, 내용)
                result.setAbttitle(abtip.getAbttitle());
                result.setAbtcontent(abtip.getAbtcontent());
                System.out.println(result.getAbttitle());

                //변경 후 저장
                int ret = abtService3.updateOneAbTip(result);
                if(ret == 1) {
                    map.put("result", "수정완료!");
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

}
