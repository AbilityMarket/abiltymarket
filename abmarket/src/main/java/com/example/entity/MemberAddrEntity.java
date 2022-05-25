package com.example.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "MEMBERADDR")
@SequenceGenerator(name = "SEQ_MEMADDR_NO", sequenceName = "SEQ_MEMADDR_NO", allocationSize = 1, initialValue = 1)

// 회원 주소 테이블
public class MemberAddrEntity {

    // 주소코드
    @Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MEMADDR_NO")
    private Long ucode;

    // 주소
    @Column(length = 150)
    private String uaddress;

    // 회원주소(경도-x)
    private Double ulongitude;
    
    // 회원주소(위도-y)
    private Double ulatitude;

    // 등록일자
    // LocalDateTime 클래스는 날짜와 시간을 표현하는 클래스 (LocalDate와 LocalTime을 합친 클래스)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime uregdate;

    // 대표주소
    private Long uchk;

    // KM설정
    private Long ukm;
    
    // 회원테이블
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "MEMBER_UID", referencedColumnName = "UID")
    private MemberEntity member;

}
