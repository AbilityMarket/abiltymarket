package com.example.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "INQUIRE")
@SequenceGenerator(name = "SEQ_INQUIRE_NO", sequenceName = "SEQ_INQUIRE_NO", allocationSize = 1, initialValue = 1)

//문의 게시판 테이블
public class InquireEntity {

    // 문의코드
    @Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_INQUIRE_NO")
    private Long inqno;

    // 문의글제목
    @Column(length = 100)
    private String inqtitle;

    // 문의글내용
    @Lob
    private String inqcontent;

    // 문의글일자
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date inqregdate;

    // 완료여부
    private Long inqtype;

    // 공개여부
    private Long inqopen;

    // 구매판매구분
    private Long inqselect;

    // 회원테이블
    @ManyToOne
    @JoinColumn(name = "MEMBER_UID", referencedColumnName = "UID")
    private MemberEntity member;

    // 문의답변게시판
    @JsonBackReference
    @OneToMany(mappedBy = "inquire")
    private List<AnswerEntity> answerList = new ArrayList<>();   
     
}
