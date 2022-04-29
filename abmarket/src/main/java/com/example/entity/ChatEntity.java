package com.example.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "CHAT")
@SequenceGenerator(name = "SEQ_CHAT_NO", sequenceName = "SEQ_CHAT_NO", allocationSize = 1, initialValue = 1)

// 채팅 테이블
public class ChatEntity {

    // 채팅코드
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CHAT_NO")
    private Long chno;

    // 보내는 사람
    private String send;

    // 받는 사람
    private String receive;

    // 채팅내역
    @Lob
    private String chcontent;

    // 등록일자
    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date chregdate;

    // 읽지 않은 메세지 수
    private Long unReadCount = 1L;

    // 채팅상태
    @Column(length = 10)
    private String chstate = "N";

    // 채팅방테이블
    @ManyToOne
    @JoinColumn(name = "CHATROOM_CRNO", referencedColumnName = "CRNO")
    private ChatroomEntity chatroom;

    // 후기테이블
    @ManyToOne
    @JoinColumn(name = "REVIEW_REVNO", referencedColumnName = "REVNO")
    private ReviewEntity review;

    // // 채팅이미지테이블
    @OneToOne
    @JoinColumn(name = "CHATIMAGE_CHINO", referencedColumnName = "CHINO")
    private ChatImageEntity chatimage;

}
