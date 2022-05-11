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

import com.fasterxml.jackson.annotation.JsonBackReference;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "ABTIPIMAGE")
@SequenceGenerator(name = "SEQ_ABTIPIMG_NO", sequenceName = "SEQ_ABTIPIMG_NO", allocationSize = 1, initialValue = 1)

// 팁 이미지 테이블
public class AbTipImageEntity {
    
    // 팁이미지코드
    @Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ABTIPIMG_NO")
    private Long abino;

    // 게시판이미지
    @Lob
    private byte[] abimage;

    // 이미지크기
    private Long abimagesize= 0L;

    // 이미지타입
    @Column(length = 30)
    private String abimagetype;

    // 이미지명
    @Column(length = 250)
    private String abimagename;

    // 일자
    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date abiregdate;

    // 팁테이블
    @JsonBackReference
    //@ManyToOne(fetch = FetchType.LAZY)
    @ManyToOne
    @JoinColumn(name = "ABTIP_ABTNO", referencedColumnName = "ABTNO")
    private AbTipEntity abtip;

}
