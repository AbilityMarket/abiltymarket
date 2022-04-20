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

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "BOARDIMAGE")
@SequenceGenerator(name = "SEQ_BOARDIMAGE_NO", sequenceName = "SEQ_BOARDIMAGE_NO", allocationSize = 1, initialValue = 1)

// 게시판 이미지 테이블
public class BoardImageEntity {

    // 게시판이미지번호
    @Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BOARDIMAGE_NO")
    private Long bino;

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

    // 일자
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date biregdate;

    // 게시판테이블
    @ManyToOne
    @JoinColumn(name = "BOARD_BNO", referencedColumnName = "BNO")
    private BoardEntity board;

}
