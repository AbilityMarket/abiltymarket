package com.example.dto;

import lombok.Data;

@Data
public class InterestDTO {

    // 관심사코드
    private Long incode;
    // 카테고리
    private String incategory;
    // 관심사이름
    private String inname;

    // 이미지
    private byte[] inimage;
    // 이미지크기
    private Long inimagesize;
    // 이미지타입
    private String inimagetype;
    // 이미지명
    private String inimagename;

    private String uid;
}
