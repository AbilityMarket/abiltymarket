package com.example.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
@Table(name = "REVIEW")
@SequenceGenerator(name = "SEQ_REVIEW_NO", sequenceName = "SEQ_REVIEW_NO", allocationSize = 1, initialValue = 1)

// 후기 테이블
public class ReviewEntity {

    // 후기번호
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_REVIEW_NO")
    private Long revno;

    // 평점
    private Long revscore;

    // 후기내용
    @Lob
    private String revcontent;

    // 후기등록일자
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @CreationTimestamp
    private Date revregdate;

    // 후기이미지테이블
    @JsonBackReference
    @OneToMany(mappedBy = "review")
    private List<ReviewImageEntity> reviewimageList = new ArrayList<>();

    // 채팅테이블
    @JsonBackReference
    @OneToMany(mappedBy = "review")
    private List<ChatEntity> chatList = new ArrayList<>();

}
