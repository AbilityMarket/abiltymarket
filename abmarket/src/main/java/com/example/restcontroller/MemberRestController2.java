package com.example.restcontroller;

import java.util.HashMap;
import java.util.Map;

import com.example.entity.MemberEntity;
import com.example.entity.RankEntity;
import com.example.jwt.JwtUtil;
import com.example.repository.MemberRespository2;
import com.example.service.MemberService1;
import com.example.service.UserDetailsServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/api/member")
public class MemberRestController2 {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    MemberService1 memberService1;

    @Autowired
    MemberRespository2 memberRespository2;

    // 로그인
    @RequestMapping(value = "/login", method = { RequestMethod.POST }, consumes = { MediaType.ALL_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> loginPost(@ModelAttribute MemberEntity member) {

        // System.out.println("HERERER" + member);
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            // 아이디 있나확인
            UserDetails user = userDetailsServiceImpl.loadUserByUsername(member.getUid());
            System.out.println("user확인용" + user.toString());

            // 암호화 되지 않은 것과 암호화 된 것 비교하기
            BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
            // 앞이 암호화되지 않은 것 뒤고 암호화된 것
            if (bcpe.matches(member.getUpw(), user.getPassword())) {
                // 토큰 만들기
                String token = jwtUtil.generatorToken(member.getUid());
                map.put("token", token);
                map.put("status", 200);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);

        }
        return map;
    }

    // 회원가입
    @RequestMapping(value = "/join", method = { RequestMethod.POST }, consumes = { MediaType.ALL_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> customerJoinPost(@ModelAttribute MemberEntity member,
            @RequestParam(name = "file", required = false) MultipartFile file) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            // 이미지 첨부
            if (file != null) {
                if (!file.isEmpty()) {
                    member.setUimage(file.getBytes());
                    member.setUimagename(file.getOriginalFilename());
                    member.setUimagesize(file.getSize());
                    member.setUimagetype(file.getContentType());
                }

            }

            // 비밀번호 암호화하기
            BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
            member.setUpw(bcpe.encode(member.getUpw()));
            // 역할 세팅
            member.setUrole("CUSTOMER");
            int ret = memberService1.insertMember(member);
            RankEntity rank = new RankEntity();
            rank.setRname("4");

            int ret1 = memberService1.insertRank(member, rank);
            // 회원가입 성공 시 200 실패시 0
            if (ret == 1 && ret1 == 1) {
                map.put("status", 200);
            }
        }

        catch (Exception e) {
            e.printStackTrace();
            // 에러발생시
            map.put("status", -1);
        }
        return map;
    }

    // 관리자 회원가입
    @RequestMapping(value = "/joinAdmin", method = { RequestMethod.POST }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> adminJoinPost(@ModelAttribute MemberEntity member,
            @RequestParam(name = "file", required = false) MultipartFile file) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            // 이미지 첨부
            if (file != null) {
                if (!file.isEmpty()) {
                    member.setUimage(file.getBytes());
                    member.setUimagename(file.getOriginalFilename());
                    member.setUimagesize(file.getSize());
                    member.setUimagetype(file.getContentType());
                }

            }

            // 비밀번호 암호화하기
            BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
            member.setUpw(bcpe.encode(member.getUpw()));
            // 역할 세팅
            member.setUrole("ADMIN");
            int ret = memberService1.insertMember(member);
            // 회원가입 성공 시 200 실패시 0
            if (ret == 1) {
                map.put("status", 200);
            }
        }

        catch (Exception e) {
            e.printStackTrace();
            // 에러발생시
            map.put("status", -1);
        }
        return map;
    }

    // 아이디 중복 확인
    @RequestMapping(value = "/check", method = { RequestMethod.GET }, consumes = { MediaType.ALL_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> customerJoinPost(
            @RequestParam(name = "uid") String uid) {
        Map<String, Object> map = new HashMap<>();
        try {
            MemberEntity member = memberService1.selectMemberOne(uid);
            // 리턴값이 있을 경우 200(중복된다)
            if (member != null) {
                map.put("status", 200);
            }
            // 리턴값이 없을 경우 0(중복되지 않는다)
            else {
                map.put("status", 0);
            }

        } catch (Exception e) {
            e.printStackTrace();
            // 에러발생시
            map.put("status", -1);
        }
        return map;
    }

    // 토큰에 해당하는 회원정보 조회하기
    // 127.0.0.1:9090/ROOT/api/member/selectmember
    @RequestMapping(value = "/selectmember", method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> selectmemberGET(
            @RequestHeader(name = "token") String token) {
        Map<String, Object> map = new HashMap<>();
        try {
            String user = jwtUtil.extractUsername(token);
            System.out.println("user정보 :" + user);
            MemberEntity member = memberRespository2.findById(user).orElse(null);

            System.out.println(member.getUname());
            if (member != null) {
                map.put("status", 200);
                map.put("uid", member.getUid());
                map.put("uname", member.getUname());
                map.put("uphone", member.getUphone());
                map.put("unickname", member.getUnickname());
                // map.put("uaddress", 애드래스 찾기);
            }

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // 비밀번호 변경
    @RequestMapping(value = "/changePw", method = { RequestMethod.POST }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> changePw(
            @RequestParam(name = "pw1") String pw1,
            @RequestParam(name = "pw2") String pw2,
            @RequestHeader(name = "token") String token) {

        // System.out.println("HERERER" + member);
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {

            String user = jwtUtil.extractUsername(token);
            System.out.println("user정보 :" + user);
            MemberEntity member = memberRespository2.findById(user).orElse(null);

            // 암호화하기
            // BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();

            // 암호화 되지 않은 것과 암호화 된 것 비교하기
            BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
            // 앞이 암호화되지 않은 것 뒤고 암호화된 것
            if (bcpe.matches(pw1, member.getUpw())) {
                // 토큰 만들기
                member.setUpw(bcpe.encode(pw2));
                memberRespository2.save(member);
                String token2 = jwtUtil.generatorToken(member.getUid());
                map.put("token", token2);
                map.put("status", 200);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);

        }
        return map;
    }
}
