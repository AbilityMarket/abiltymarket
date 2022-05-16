package com.example.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.Immutable;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Immutable
@Table(name = "BOARDANDWRITER")
public class BoardAndWriter {
    // 게시판 번호
    @Id
    Long bno;

    // 제목
    private String btitle;

    // 종류
    // private Long btag;

    // 구매 =1L 판매 2L
    private Long brole;

    // 등록일자
    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date bregdate;

    // 주소
    private String baddress;

    // 보드 이미지
    private byte[] bimage;

    private Long bimagesize;

    private String bimagename;

    private String bimagetype;

    // 게시글 작성자 아이디
    private String uid;

    // 작성자 이미지
    private byte[] uimage;

    private Long uimagesize = 0L;

    private String uimagetype;

    private String uimagename;

    // 작성자 신고당했니?
    private Long ureported;

    // 작성자 닉네임
    private String unickname;

    private Long incode;

    private String inname;

    private String incategory;

}
