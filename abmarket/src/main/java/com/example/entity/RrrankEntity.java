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

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "RRRANK")
@SequenceGenerator(name = "SEQ_RRRANK_NO", sequenceName = "SEQ_RRRANK_NO", allocationSize = 1, initialValue = 1)

// 등급 매기기 테이블
public class RrrankEntity {

    // 등급매기기번호
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_RRRANK_NO")
    private Long rkno;

    // 일자
    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date rrregdate;

    // 회원테이블
    @ManyToOne
    @JoinColumn(name = "MEMBER_UID", referencedColumnName = "UID")
    private MemberEntity member;

    // 등급
    @ManyToOne
    @JoinColumn(name = "RANK_RNAME", referencedColumnName = "RNAME")
    private RankEntity rank;

}
