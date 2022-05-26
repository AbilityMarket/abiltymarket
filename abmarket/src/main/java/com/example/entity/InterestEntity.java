package com.example.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "INTEREST")
@SequenceGenerator(name = "SEQ_INTEREST_NO", sequenceName = "SEQ_INTEREST_NO", allocationSize = 1, initialValue = 1)

// 관심사 테이블
public class InterestEntity {

    // 관심사코드
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_INTEREST_NO")
    private Long incode;

    // 관심사이름
    @Column(length = 50)
    private String inname;

    // 분류
    private String incategory;

    // 이미지
    @Lob
    @Column(nullable = true)
    private byte[] inimage;

    // 이미지크기
    private Long inimagesize = 0L;

    // 이미지타입
    @Column(length = 30)
    private String inimagetype;

    // 이미지명
    @Column(length = 250)
    private String inimagename;

    // 일자
    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date inregdate;

    // 회원관심사테이블
    @JsonBackReference
    @OneToMany(mappedBy = "interest", cascade = CascadeType.REMOVE)
    private List<MeminterestEntity> meminterestList = new ArrayList<>();

    @JsonBackReference
    @OneToMany(mappedBy = "interest", cascade = CascadeType.REMOVE)
    private List<BoardInterest> BoardInterestList = new ArrayList<>();

}
