package com.example.restcontroller;

import com.example.service.ReviewImageService1;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/reviewimage")
public class ReviewImageRestController1 {

    @Autowired
    ReviewImageService1 RevImgService1;

}
