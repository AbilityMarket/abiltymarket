package com.example.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "BOARD")
@SequenceGenerator(name = "SEQ_BOARD_NO", sequenceName = "SEQ_BOARD_NO", allocationSize = 1, initialValue = 1)

// 게시판 테이블
public class BoardEntity {

    // 게시판번호
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BOARD_NO")
    private Long bno;

    // 글제목
    @Column(length = 200)
    private String btitle;

    // 내용
    @Lob
    private String bcontent;

    // 조회수
    private Long bhit = 1L;

    // 가격
    private Long bprice;

    // 등록일자
    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date bregdate;

    // 구매OR판매
    private Long brole;

    // 완료여부
    private Long bdone;

    // 모집인원수
    private Long bcount;

    // 마감 날짜
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date benddate;

    // 주소
    @Column(length = 150)
    private String baddress;

    // 주소(경도-x)
    private Double blongitude;

    // 주소(위도-y)
    private Double blatitude;

    // 게시판이미지
    @Lob
    private byte[] bimage;

    // 이미지크기
    private Long bimagesize = 0L;

    // 이미지타입
    @Column(length = 30)
    private String bimagetype;

    // 이미지명
    @Column(length = 250)
    private String bimagename;

    // 회원테이블
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "MEMBER_UID", referencedColumnName = "UID")
    private MemberEntity member;

    // 게시판댓글테이블
    @JsonManagedReference
    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<CommEntity> commList = new ArrayList<>();

    // 게시판찜하기테이블
    @JsonManagedReference
    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<BolikeEntity> bolikeList = new ArrayList<>();

    // 게시판이미지테이블
    @JsonManagedReference
    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<BoardImageEntity> boardimageList = new ArrayList<>();

    // 게시글신고
    @JsonManagedReference
    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<ReportEntity> reportList = new ArrayList<>();

    // 채팅방테이블
    @JsonManagedReference
    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<ChatroomEntity> chatroomList = new ArrayList<>();

    // 게시판인원수
    @JsonManagedReference
    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<BcountEntity> bcountList = new ArrayList<>();

    // 게시판관심사
    @JsonManagedReference
    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<BoardInterest> BoardInterestList = new ArrayList<>();

}
