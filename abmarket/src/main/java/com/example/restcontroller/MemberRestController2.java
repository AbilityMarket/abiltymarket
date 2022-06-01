package com.example.restcontroller;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.example.entity.MemberEntity;
import com.example.entity.RankEntity;
import com.example.jwt.JwtUtil;
import com.example.repository.MemAddrRepository3;
import com.example.repository.MemberRespository2;
import com.example.service.MemberService1;
import com.example.service.UserDetailsServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.ModelAttribute;
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

    @Autowired
    ResourceLoader resLoader;

    @Autowired
    MemAddrRepository3 memAddrRepository3;

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

    // 회원가입 1페이지
    @RequestMapping(value = "/join", method = { RequestMethod.POST }, consumes = { MediaType.ALL_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> customerJoinPost(
            @ModelAttribute MemberEntity member) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {

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

    // 회원가입 2페이지
    @RequestMapping(value = "/joinnext", method = { RequestMethod.POST }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> joinnext(
            @RequestParam(name = "uid") String uid,
            // @RequestParam(name = "unickname") String unickname,
            @RequestParam(name = "file", required = false) MultipartFile file) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            MemberEntity member = new MemberEntity();
            // member.setUnickname(unickname);
            // 이미지 첨부
            if (file != null) {
                if (!file.isEmpty()) {
                    member.setUimage(file.getBytes());
                    member.setUimagename(file.getOriginalFilename());
                    member.setUimagesize(file.getSize());
                    member.setUimagetype(file.getContentType());
                }
            }
            memberRespository2.save(member);
        } catch (Exception e) {
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

    // 회원 탈퇴
    @RequestMapping(value = "/leave", method = { RequestMethod.DELETE }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> leave(
            @RequestParam(name = "pw1") String pw1,
            @RequestHeader(name = "token") String token) {

        // System.out.println("HERERER" + member);
        System.out.println(pw1);
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
                memberRespository2.deleteById(member.getUid());
                map.put("status", 200);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);

        }
        return map;
    }

    // uid로 멤버 이미지 조회하기
    // 127.0.0.1:9090/ROOT/api/member/image?uid=uid
    @RequestMapping(value = "/image") // url 주소생성
    public ResponseEntity<byte[]> imageGET(
            @RequestParam(name = "uid") String uid)
            throws IOException {

        MemberEntity member = memberRespository2.findById(uid).orElse(null);

        // 이미지가 있을때
        if (member.getUimagesize() > 0) { // 첨부한 파일 존재
            HttpHeaders headers = new HttpHeaders();

            if (member.getUimagetype().equals("image/jpeg")) {
                headers.setContentType(MediaType.IMAGE_JPEG);
            } else if (member.getUimagetype().equals("image/png")) {
                headers.setContentType(MediaType.IMAGE_PNG);
            } else if (member.getUimagetype().equals("image/gif")) {
                headers.setContentType(MediaType.IMAGE_GIF);
            }

            // 이미지 byte[], headers, HttpStatus.Ok
            ResponseEntity<byte[]> response = new ResponseEntity<>(member.getUimage(),
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

    // 회원 이미지 수정
    @RequestMapping(value = "/updateimg", method = { RequestMethod.PUT }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> updateimg(
            @RequestParam(name = "file") MultipartFile file,
            @RequestHeader(name = "token") String token)
            throws IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            String uid = jwtUtil.extractUsername(token);
            MemberEntity member = memberRespository2.findById(uid).orElse(null);
            // 이미지 첨부
            if (file != null) {
                if (!file.isEmpty()) {
                    member.setUimage(file.getBytes());
                    member.setUimagename(file.getOriginalFilename());
                    member.setUimagesize(file.getSize());
                    member.setUimagetype(file.getContentType());
                    memberRespository2.save(member);
                    map.put("status", 200);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);

        }
        return map;
    }

    // 회원정보 수정
    @RequestMapping(value = "/changeInfo", method = { RequestMethod.POST }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> changeInfo(
            @RequestParam(name = "unickname") String unickname,
            @RequestParam(name = "uphone") String uphone,
            @RequestHeader(name = "token") String token) {

        // System.out.println("HERERER" + member);
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {

            String uid = jwtUtil.extractUsername(token);
            System.out.println("user정보 :" + uid);
            MemberEntity member = memberRespository2.findById(uid).orElse(null);
            // MemberAddrEntity memberAddr = memAddrRepository3.findBy
            if (member != null) {
                member.setUnickname(unickname);
                member.setUphone(uphone);
                memberRespository2.save(member);
                map.put("status", 200);
            }

        } catch (

        Exception e) {
            e.printStackTrace();
            map.put("status", -1);

        }
        return map;
    }

    // 문의글 상세페이지 접속을 위해서 필요
    // 127.0.0.1:9090/ROOT/api/member/secret
    @RequestMapping(value = "/secret", method = { RequestMethod.POST }, consumes = { MediaType.ALL_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> secretPost(
            @RequestParam(name = "pw1") String pw1,
            @RequestHeader(name = "token") String token) {
        System.out.println("pw1 => " + pw1);
        System.out.println("token => " + token);
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);

        try {
            String user = jwtUtil.extractUsername(token);
            System.out.println("user =>" + user);
            MemberEntity member = memberRespository2.findById(user).orElse(null);

            BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
            if (bcpe.matches(pw1, member.getUpw())) {
                System.out.println("bcpe!!" + bcpe);

                // map.put("result", "동일")
                map.put("status", 200);
            }

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);

        }
        return map;
    }
}
