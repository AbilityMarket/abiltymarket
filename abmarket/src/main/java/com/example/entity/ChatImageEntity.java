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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "CHATIMAGE")
@SequenceGenerator(name = "SEQ_CHATIMG_NO", sequenceName = "SEQ_CHATIMG_NO", allocationSize = 1, initialValue = 1)

// 채팅 이미지 테이블
public class ChatImageEntity {

    // 채팅이미지번호
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CHATIMG_NO")
    private Long chino;

    // 채팅이미지
    @Lob
    private byte[] chimage;

    // 채팅이미지크기
    private Long chimagesize = 0L;

    // 채팅이미지타입
    @Column(length = 30)
    private String chimagetype;

    // 채팅이미지명
    @Column(length = 250)
    private String chimagename;

    // 일자
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date chregdate;

    // 채팅테이블

    @OneToOne(mappedBy = "chatimage")
    private ChatEntity chat;

}
