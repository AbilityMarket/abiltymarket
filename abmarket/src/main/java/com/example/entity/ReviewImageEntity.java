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
@Table(name = "REVIEWIMAGE")
@SequenceGenerator(name = "SEQ_REVIEWIMG_NO", sequenceName = "SEQ_REVIEWIMG_NO", allocationSize = 1, initialValue = 1)

// 후기 이미지 테이블
public class ReviewImageEntity {
    
    // 후기이미지번호
    @Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_REVIEWIMG_NO")
    private Long rvimno;

    // 후기이미지
    @Lob
    private byte[] rvimage;

    // 후기이미지사이즈
    private Long rvimagesize = 0L;

    // 후기이미지타입
    @Column(length = 30)
    private String rvimagetype;

    // 후기이미지명
    @Column(length = 250)
    private String rvimagename;

    // 일자
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date rviregdate;
    
    // 후기테이블
    @ManyToOne
    @JoinColumn(name = "REVIEW_REVNO", referencedColumnName = "REVNO")
    private ReviewEntity review;

}
