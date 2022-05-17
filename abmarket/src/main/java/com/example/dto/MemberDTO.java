package com.example.dto;

import java.util.Date;

import lombok.Data;

@Data
public class MemberDTO {

    // 회원 아이디
    private String uid;
    // 회원 암호
    private String upw;
    // 회원 이름
    private String uname;
    // 회원 전화번호
    private String uphone;
    // 이용자 or 관리자
    private String urole;
    // 등록일자
    private Date uregdate;

    // 이미지
    private byte[] uimage;
    // 이미지크기
    private Long uimagesize;
    // 이미지타입
    private String uimagetype;
    // 이미지명
    private String uimagename;

}
