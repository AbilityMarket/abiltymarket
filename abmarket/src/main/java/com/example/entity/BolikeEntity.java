package com.example.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "BOLIKE")
@SequenceGenerator(name = "SEQ_BOLIKE_NO", sequenceName = "SEQ_BOLIKE_NO", allocationSize = 1, initialValue = 1)

// 게시판 찜하기 테이블
public class BolikeEntity {

    // 찜번호
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BOLIKE_NO")
    private Long bolno;

    // 찜일자
    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date boregdate;

    // 회원테이블
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "MEMBER_UID", referencedColumnName = "UID")
    private MemberEntity member;

    // 게시판테이블
    @JsonBackReference
    @ManyToOne
    @JsonIgnoreProperties({ "member" }) // 해당 게시판(board) 중 글 작성자(member)는 필요없음
    @JoinColumn(name = "BOARD_BNO", referencedColumnName = "BNO")
    private BoardEntity board;
}
