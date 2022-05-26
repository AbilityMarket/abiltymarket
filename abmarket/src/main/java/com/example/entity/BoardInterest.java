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

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

// 게시판관심사
@Data
@Entity
@Table(name = "BOARDINTEREST")
@SequenceGenerator(name = "SEQ_BOARDINTEREST_NO", sequenceName = "SEQ_BOARDINTEREST_NO", allocationSize = 1, initialValue = 1)
public class BoardInterest {
    // 번호
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BOARDINTEREST_NO")
    private Long boino;

    // 등록일자
    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date boiregdate;

    // 게시판테이블
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "bno", referencedColumnName = "bno")
    private BoardEntity board;

    // 관심사테이블
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "incode", referencedColumnName = "incode")
    private InterestEntity interest;
}
