package com.example.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.example.dto.InterestDTO1;
import com.example.dto.MemberDTO;
import com.example.entity.InterestEntity;
import com.example.mapper.AdminMapper1;
import com.example.mapper.AdminMapper2;
import com.example.service.InterestService1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/api/admin")
public class InterestController1 {

    @Autowired
    AdminMapper1 adminMapper1;

    @Autowired
    ResourceLoader resLoader;

    @GetMapping(value = "/interest")
    public String adminInterestGET(
            HttpSession httpSession,
            @RequestParam(name = "txt", defaultValue = "") String txt,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "select", defaultValue = "1") int select,
            Model model) {

        if (select == 1) {
            List<InterestEntity> list = adminMapper1.selectListInterest(txt, page * 10 - 9, page * 10);
            model.addAttribute("list", list);
            long cnt = adminMapper1.selectInterestInnameCount(txt);
            model.addAttribute("pages", (cnt - 1) / 10 + 1);
            model.addAttribute("select", 1);
        }

        return "admin/interest";
    }

    // @GetMapping(value = "/image")
    // public ResponseEntity<byte[]> imageGET(
    // @RequestParam(name = "incode") long incode) throws IOException {

    // InterestDTO1 interest = adminMapper1.selectInterestImage(incode);

    // if (interest != null) {
    // if (interest.getInimagesize() > 0) { // 첨부한 파일 존재
    // HttpHeaders headers = new HttpHeaders();

    // if (interest.getInimagetype().equals("image/jpeg")) {
    // headers.setContentType(MediaType.IMAGE_JPEG);
    // } else if (interest.getInimagetype().equals("image/png")) {
    // headers.setContentType(MediaType.IMAGE_PNG);
    // } else if (interest.getInimagetype().equals("image/gif")) {
    // headers.setContentType(MediaType.IMAGE_GIF);
    // }

    // // 이미지 byte[], headers, HttpStatus.Ok
    // ResponseEntity<byte[]> response = new ResponseEntity<>(interest.getInimage(),
    // headers, HttpStatus.OK);
    // return response;
    // } else {
    // InputStream is = resLoader
    // .getResource("classpath:/static/images/피카추.png")
    // .getInputStream();

    // HttpHeaders headers = new HttpHeaders();
    // headers.setContentType(MediaType.IMAGE_JPEG);

    // ResponseEntity<byte[]> response = new ResponseEntity<>(is.readAllBytes(),
    // headers, HttpStatus.OK);

    // return response;
    // }
    // }
    // return null;
    // }

}