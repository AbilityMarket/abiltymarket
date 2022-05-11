package com.example.dto;

import lombok.Data;

@Data
public class MemberDTO {

    private String uid;
    // 이미지
    private byte[] uimage;
    // 이미지크기
    private Long uimagesize;
    // 이미지타입
    private String uimagetype;
    // 이미지명
    private String uimagename;

}
