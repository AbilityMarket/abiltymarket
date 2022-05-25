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
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "INQUIRE")
@SequenceGenerator(name = "SEQ_INQUIRE_NO", sequenceName = "SEQ_INQUIRE_NO", allocationSize = 1, initialValue = 1)

// Json으로 변환시 오류 방지를 위한 코드(1개 조회 시 작성자와 토큰 비교 중 오류가 나서 추가함)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })

// 문의 게시판 테이블
public class InquireEntity {

    // 문의코드
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_INQUIRE_NO")
    private Long inqno;

    // 문의글제목
    @Column(length = 100)
    private String inqtitle;

    // 문의글유형
    // 0.거래문의, 1.계정문의, 2.광고문의, 3.오류제보, 4.이벤트/프로모션
    // 5.능력마켓사용방법, 6.게시글노출/비노출, 7.비매너사용자신고, 8. 기타문의
    private Long inqselecttype;

    // 문의글내용
    @Lob
    private String inqcontent;

    // 문의글일자
    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date inqregdate;

    // 답변완료여부 (1->완료X, 0->완료)
    private Long inqtype = 1L;

    // 구매판매구분 (1->구매, 2->판매)
    private Long inqselect = 1L;

    // 문의글, FAQ 구분 (1->문의글, 2->FAQ)
    private Long inqfaqselect = 1L;

    // 회원테이블
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "MEMBER_UID", referencedColumnName = "UID")
    private MemberEntity member;

    // 문의답변게시판
    @JsonManagedReference
    @OneToMany(mappedBy = "inquire", cascade = CascadeType.REMOVE)
    private List<AnswerEntity> answerList = new ArrayList<>();

}
