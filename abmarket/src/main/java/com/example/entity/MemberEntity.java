package com.example.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "MEMBER")
// 회원 테이블
public class MemberEntity {

    // 회원아이디
    @Id
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
    private String urole;

    // 등록일자
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @CreationTimestamp
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

    // 문의답변테이블
    @JsonManagedReference
    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<AnswerEntity> answerlist = new ArrayList<>();

    // 회원주소테이블
    @JsonManagedReference
    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<MemberAddrEntity> memberaddrList = new ArrayList<>();

    // 회원관심사테이블
    @JsonManagedReference
    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<MeminterestEntity> meminterestList = new ArrayList<>();

    // 게시판테이블
    @JsonManagedReference
    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<BoardEntity> boardList = new ArrayList<>();

    // 게시판찜하기테이블
    @JsonManagedReference
    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<BolikeEntity> bolikeList = new ArrayList<>();

    // 게시판댓글테이블
    @JsonManagedReference
    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<CommEntity> commList = new ArrayList<>();

    // 팁테이블
    @JsonManagedReference
    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<AbTipEntity> abtipList = new ArrayList<>();

    // 문의게시판
    @JsonManagedReference
    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<InquireEntity> inquireList = new ArrayList<>();

    // 알림테이블
    @JsonManagedReference
    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<AlertEntity> alertList = new ArrayList<>();

    // 게시글신고
    @JsonManagedReference
    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<ReportEntity> reportList = new ArrayList<>();

    // 등급매기기
    @JsonManagedReference
    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<RrrankEntity> rrrankList = new ArrayList<>();

    // 채팅방테이블
    @JsonManagedReference
    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<ChatroomEntity> chatroomList = new ArrayList<>();

}

// 좋아요
// @JsonManagedReference
// @OneToMany(mappedBy = "commentchg", cascade = CascadeType.REMOVE)
// private List<CmtLikeCHG> cmtLikechglist = new ArrayList<>();