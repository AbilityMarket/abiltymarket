package com.example.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "RECOMMENT")
@SequenceGenerator(name = "SEQ_RECOMM_NO", sequenceName = "SEQ_RECOMM_NO", allocationSize = 1, initialValue = 1)

// 게시판 대댓글 게시판 테이블
public class RecommentEntity {

    // 대댓글번호
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_RECOMM_NO")
    private Long reno;

    // 대댓글내용
    @Lob
    private String recontent;

    // 대댓글등록일자
    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date reregdate;

    // 대댓글공개여부
    private Long rereopen;

    // 게시판댓글테이블
    @ManyToOne
    @JoinColumn(name = "COMM_CONO", referencedColumnName = "CONO")
    private CommEntity comm;

    // 회원테이블
    @ManyToOne
    @JoinColumn(name = "MEMBER_UID", referencedColumnName = "UID")
    private MemberEntity member;

}
