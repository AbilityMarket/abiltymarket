package com.example.entity;

import java.util.Date;

import javax.persistence.Column;
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
@Table(name = "REPORT")
@SequenceGenerator(name = "SEQ_REPORT_NO", sequenceName = "SEQ_REPORT_NO", allocationSize = 1, initialValue = 1)

// 게시글 신고 테이블
public class ReportEntity {

    // 신고코드
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_REPORT_NO")
    private Long repcode;

    // 신고제목
    @Column(length = 200)
    private String reptitle;

    // 신고내용
    @Lob
    private String repcontent;

    // 신고일자
    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date repregdate;

    // 신고유형
    private Long retype;

    // 회원테이블
    @ManyToOne
    @JoinColumn(name = "MEMBER_UID", referencedColumnName = "UID")
    private MemberEntity member;

    // 게시판테이블
    @ManyToOne
    @JoinColumn(name = "BOARD_BNO", referencedColumnName = "BNO")
    private BoardEntity board;

}
