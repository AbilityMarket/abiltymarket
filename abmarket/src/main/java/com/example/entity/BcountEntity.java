package com.example.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "BCOUNT")
@SequenceGenerator(name = "SEQ_BCOUNT_NO", sequenceName = "SEQ_BCOUNT_NO", allocationSize = 1, initialValue = 1)


// 게시판 인원수 테이블
public class BcountEntity {

    // 게시판인원번호
    @Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BCOUNT_NO")
    private Long bcno;

    // 일자
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date bcrergdate;

    // 참가자수
    private Long bcount;

    // IN OR OUT
    @Column(length = 10)
    private String bctype;
    
    // 게시판테이블
    @ManyToOne
    @JoinColumn(name = "BOARD_BNO", referencedColumnName = "BNO")
    private BoardEntity board;

}
