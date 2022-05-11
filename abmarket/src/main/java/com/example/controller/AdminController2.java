package com.example.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.example.dto.MemberDTO;
import com.example.entity.MemberEntity;
import com.example.mapper.AdminMapper2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/api/admin")
public class AdminController2 {

    @Autowired
    AdminMapper2 adminMapper2;

    @Autowired
    ResourceLoader resLoader;

    @GetMapping(value = { "/", "/home" })
    public String getAdminHome(
            HttpSession httpSession,
            @RequestParam(name = "select", defaultValue = "1") int select,
            @RequestParam(name = "txt", defaultValue = "") String txt,
            @RequestParam(name = "page", defaultValue = "1") int page,
            Model model) {
        System.out.println(select);
        // 아이디로 검색
        if (select == 1) {

            List<MemberEntity> list = adminMapper2.selectListMemberUid(txt, page * 10 - 9, page * 10);
            model.addAttribute("list", list);
            long pagecnt = adminMapper2.selectMemberUidCount(txt);
            model.addAttribute("pages", (pagecnt - 1) / 10 + 1);
            model.addAttribute("select", 1);
        }
        // 닉네임으로 검색
        else if (select == 2) {
            List<MemberEntity> list = adminMapper2.selectListMemberUnickname(txt, page * 10 - 9, page * 10);
            model.addAttribute("list", list);
            long pagecnt = adminMapper2.selectMemberUnicknameCount(txt);
            model.addAttribute("pages", (pagecnt - 1) / 10 + 1);
            model.addAttribute("select", 2);
        }

        return "admin/home";
    }

    @GetMapping(value = "/image")
    public ResponseEntity<byte[]> imageGET(
            @RequestParam(name = "uid") String uid) throws IOException {
        MemberDTO retMemberImg = adminMapper2.selectMemberImage(uid);

        if (retMemberImg != null) { // 물품정보가 존재하면
            if (retMemberImg.getUimagesize() > 0) { // 첨부한 파일 존재
                HttpHeaders headers = new HttpHeaders();

                if (retMemberImg.getUimagetype().equals("image/jpeg")) {
                    headers.setContentType(MediaType.IMAGE_JPEG);
                } else if (retMemberImg.getUimagetype().equals("image/png")) {
                    headers.setContentType(MediaType.IMAGE_PNG);
                } else if (retMemberImg.getUimagetype().equals("image/gif")) {
                    headers.setContentType(MediaType.IMAGE_GIF);
                }

                // 이미지 byte[], headers, HttpStatus.Ok
                ResponseEntity<byte[]> response = new ResponseEntity<>(retMemberImg.getUimage(),
                        headers, HttpStatus.OK);
                return response;
            } else {
                InputStream is = resLoader
                        .getResource("classpath:/static/images/디그다.png")
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
