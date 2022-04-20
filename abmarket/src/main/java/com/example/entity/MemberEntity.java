package com.example.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "MEMBER")
@SequenceGenerator(name = "SEQ_MEMBER_NO", sequenceName = "SEQ_MEMBER_NO", allocationSize = 1, initialValue = 1)

// 회원 테이블
public class MemberEntity {
    
    // 회원아이디
    @Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MEMBER_NO")
    @Column(length = 30)
    private String uid;

    // 회원암호
    @Column(length = 200)
    private String upw;

    // 회원이름
    @Column(length = 15)
    private String uname;

    // 회원전화번호
    @Column(length = 15)
    private String uphone;

    // 이용자OR관리자
    private Long urole;

    // 등록일자
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date uregdate;

    // 회원이미지
    @Lob
    @Column(nullable = true)
    private byte[] uimage;

    // 이미지크기
    private Long uimagesize = 0L;

    // 이미지타입
    @Column(length = 30)
    private String uimagetype;

    // 이미지명
    @Column(length = 250)
    private String uimagename;

    // 신고당한여부
    private Long ureported;

    // 회원닉네임
    @Column(length = 30)
    private String unickname;

    // 회원주소테이블
    @JsonBackReference
    @OneToMany(mappedBy = "member")
    private List<MemberAddrEntity> memberaddrList = new ArrayList<>();

    // 회원관심사테이블
    @JsonBackReference
    @OneToMany(mappedBy = "member")
    private List<MeminterestEntity> meminterestList = new ArrayList<>();

    // 게시판테이블
    @JsonBackReference
    @OneToMany(mappedBy = "member")
    private List<BoardEntity> boardList = new ArrayList<>();

    // 게시판찜하기테이블
    @JsonBackReference
    @OneToMany(mappedBy = "member")
    private List<BolikeEntity> bolikeList = new ArrayList<>();

    // 게시판댓글테이블
    @JsonBackReference
    @OneToMany(mappedBy = "member")
    private List<CommEntity> commList = new ArrayList<>();

    // 문의게시판
    @JsonBackReference
    @OneToMany(mappedBy = "member")
    private List<InquireEntity> inquireList = new ArrayList<>();

    // 게시글신고
    @JsonBackReference
    @OneToMany(mappedBy = "member")
    private List<ReportEntity> reportList = new ArrayList<>();

    // 등급매기기
    @JsonBackReference
    @OneToMany(mappedBy = "member")
    private List<RrrankEntity> rrrankList = new ArrayList<>();

    // 알림테이블
    @JsonBackReference
    @OneToMany(mappedBy = "member")
    private List<AlertEntity> alertList = new ArrayList<>();

    // 채팅방테이블
    @JsonBackReference
    @OneToMany(mappedBy = "member")
    private List<ChatroomEntity> chatroomList = new ArrayList<>();

}
