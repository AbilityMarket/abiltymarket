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

import com.fasterxml.jackson.annotation.JsonBackReference;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "COMM")
@SequenceGenerator(name = "SEQ_COMM_NO", sequenceName = "SEQ_COMM_NO", allocationSize = 1, initialValue = 1)

// 게시판 댓글 테이블
public class CommEntity {

    // 댓글번호
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_COMM_NO")
    private Long cono;

    // 댓글내용
    @Lob
    private String cocontent;

    // 댓글등록일자
    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date coregdate;

    // 공개여부
    private Long coopen;

    // 게시판테이블
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "bno", referencedColumnName = "bno")
    private BoardEntity board;

    // 회원테이블
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "MEMBER_UID", referencedColumnName = "UID")
    private MemberEntity member;

}
