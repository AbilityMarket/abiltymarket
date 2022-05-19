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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "CHATROOM")
@SequenceGenerator(name = "SEQ_CHATRM_NO", sequenceName = "SEQ_CHATRM_NO", allocationSize = 1, initialValue = 1)

// 채팅방 테이블
public class ChatroomEntity {

    // 채팅방번호
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CHATRM_NO")
    private Long crno;

    // 생성일자
    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date crregdate;

    // 신고여부
    @Column(length = 10)
    private String crreport = "N";

    // 대화여부
    private Long startMessage = 0L;

    // 게시판테이블
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "BOARD_BNO", referencedColumnName = "BNO")
    private BoardEntity board;

    // 회원테이블
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "MEMBER_UID", referencedColumnName = "UID")
    private MemberEntity member;

    // 채팅테이블
    @JsonManagedReference
    @OneToMany(mappedBy = "chatroom")
    private List<ChatEntity> chatList = new ArrayList<>();

}
