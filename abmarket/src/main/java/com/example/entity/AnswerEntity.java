package com.example.entity;

import java.util.Date;

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
@Table(name = "ANSWER")
@SequenceGenerator(name = "SEQ_ANSWER_NO", sequenceName = "SEQ_ANSWER_NO", allocationSize = 1, initialValue = 1)

// 문의 답변 게시판 테이블
public class AnswerEntity {

    // 답변코드
    @Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ANSWER_NO")
    private Long anno;

    // 답변글내용
    @Lob
    private String ancontent;

    // 답변글일자
    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date anregdate;
    
    // 문의게시판
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "INQUIRE_INQNO", referencedColumnName = "INQNO")
    private InquireEntity inquire;
    
}
