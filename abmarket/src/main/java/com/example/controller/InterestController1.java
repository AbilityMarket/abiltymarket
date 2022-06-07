package com.example.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.example.dto.InterestDTO;
import com.example.entity.InterestEntity;
import com.example.jwt.JwtUtil;
import com.example.mapper.AdminMapper1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value = "/api/admin/interest")
public class InterestController1 {

    @Autowired
    AdminMapper1 adminMapper1;

    @Autowired
    ResourceLoader resLoader;

    @Autowired
    JwtUtil jwtUtil;

    // 127.0.0.1:9090/ROOT/api/admin/interest/insert

    // 관심사 작성
    @GetMapping(value = "/insert")
    public String insertGET() {
        return "admin/interest/insert";
    }

    @PostMapping(value = "/insert")
    public String insertPOST(
            @AuthenticationPrincipal User user,
            @ModelAttribute InterestDTO interest,
            @RequestParam(name = "file") MultipartFile file) throws IOException {

        // System.out.println("interest : " + interest.toString());
        // System.out.println(file.getOriginalFilename());
        // System.out.println(user.toString());
        if (user != null) {
            // System.out.println("user : " + user.toString());
            interest.setInimage(file.getBytes());
            interest.setInimagename(file.getOriginalFilename());
            interest.setInimagesize(file.getSize());
            interest.setInimagetype(file.getContentType());

            interest.setIncategory(interest.getIncategory());
            interest.setInname(interest.getInname());

            interest.setUid(user.getUsername());
            adminMapper1.insertInterestOne(interest);

            return "redirect:/api/admin/interest/home";
        }
        // 로그인 되지 않았을 때
        return "redirect:/api/admin/";
    }

    // 127.0.0.1:9090/ROOT/api/admin/interest/update
    // 수정하기
    @GetMapping(value = "/update")
    public String updateGET(
            Model model,
            @AuthenticationPrincipal User user,
            @RequestParam(name = "code") long code) {
        if (user != null) { // 로그인 되었을때
            InterestDTO interest = adminMapper1.selectInterestOne(code);
            model.addAttribute("interest", interest);
            // System.out.println("===== interest ===== " + interest);
            return "admin/interest/update";
        }
        // return "redirect:/member/login";
        return "redirect:/api/admin/";

    }

    @PostMapping(value = "/update")
    public String updatePOST(
            Model model,
            @AuthenticationPrincipal User user,
            @ModelAttribute InterestDTO interest,
            @RequestParam(name = "image") MultipartFile file) throws IOException {
        // System.out.println(interest.toString());
        if (user != null) { // 로그인 되었을때
            // System.out.println(user.toString());
            if (!file.isEmpty()) { // 이미지 첨부가 되었다면
                interest.setInimage(file.getBytes());
                interest.setInimagename(file.getOriginalFilename());
                interest.setInimagesize(file.getSize());
                interest.setInimagetype(file.getContentType());
            }
            interest.setIncategory(interest.getIncategory());
            interest.setInname(interest.getInname());

            interest.setUid(user.getUsername());
            adminMapper1.updateInterestOne(interest);

            // 알림창 필요시
            // model.addAttribute("msg", "물품변경 완료");
            // model.addAttribute("url", "/api/admin/interest/");
            // return "alert";

            // 알림창 필요없으면
            return "redirect:/api/admin/interest/home";
        }
        // 로그이 되지 않았을 경우
        // return "redirect:/member/login";
        return "redirect:/api/admin/";
    }

    // 127.0.0.1:9090/ROOT/api/admin/interest/delete
    // 삭제하기(1개)
    @PostMapping(value = "/delete")
    public String deletePost(
            @AuthenticationPrincipal User user,
            @RequestParam(name = "code") long code) {
        if (user != null) { // 로그이 되었을때
            // System.out.println(code);
            int ret = adminMapper1.deleteInterestOne(code);
            if (ret == 1) {
                return "redirect:/api/admin/interest/home";
            }
            return "redirect:/api/admin/interest/home";
        }
        // 로그인이 되지 않았을 경우
        // return "redirect:/member/login";
        return "redirect:/api/admin/";
    }

    // 페이지네이션 + 검색
    @GetMapping(value = { "/", "/home" })
    public String adminInterestGET(
            HttpSession httpSession,
            @RequestParam(name = "txt", defaultValue = "") String txt,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "select", defaultValue = "1") int select,
            Model model) {

        // 카테고리로 검색
        if (select == 1) {
            List<InterestEntity> list = adminMapper1.selectListInterestIncategory(txt, (page * 10) - (10 - 1),
                    page * 10);
            model.addAttribute("list", list);
            long pagecnt = adminMapper1.selectInterestIncategoryCount(txt);
            model.addAttribute("pages", (pagecnt - 1) / 10 + 1);
            model.addAttribute("select", 1);
        }
        // 관심사로 검색
        else if (select == 2) {
            List<InterestEntity> list = adminMapper1.selectListInterestInname(txt, (page * 10) - (10 - 1), page * 10);
            model.addAttribute("list", list);
            long cnt = adminMapper1.selectInterestInnameCount(txt);
            model.addAttribute("pages", (cnt - 1) / 10 + 1);
            model.addAttribute("select", 2);
        }
        return "admin/interest/interest";
    }

    // /api/admin/interest/image
    @GetMapping(value = "/image")
    public ResponseEntity<byte[]> imageGET(
            @RequestParam(name = "incode") long incode) throws IOException {

        InterestDTO interest = adminMapper1.selectInterestImage(incode);

        if (interest != null) {
            if (interest.getInimagesize() > 0) { // 첨부한 파일 존재
                HttpHeaders headers = new HttpHeaders();

                if (interest.getInimagetype().equals("image/jpeg")) {
                    headers.setContentType(MediaType.IMAGE_JPEG);
                } else if (interest.getInimagetype().equals("image/png")) {
                    headers.setContentType(MediaType.IMAGE_PNG);
                } else if (interest.getInimagetype().equals("image/gif")) {
                    headers.setContentType(MediaType.IMAGE_GIF);
                }

                // 이미지 byte[], headers, HttpStatus.Ok
                ResponseEntity<byte[]> response = new ResponseEntity<>(interest.getInimage(),
                        headers, HttpStatus.OK);
                return response;
            } else {
                InputStream is = resLoader
                        .getResource("classpath:/static/images/피카추.png")
                        .getInputStream();

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_JPEG);

                ResponseEntity<byte[]> response = new ResponseEntity<>(is.readAllBytes(),
                        headers, HttpStatus.OK);

                return response;
            }
        }
        return null;
    }

}