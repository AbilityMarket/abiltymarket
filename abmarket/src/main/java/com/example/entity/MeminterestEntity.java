package com.example.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "MEMINTEREST")
@SequenceGenerator(name = "SEQ_MEMINST_NO", sequenceName = "SEQ_MEMINST_NO", allocationSize = 1, initialValue = 1)

// 회원 관심사 테이블
public class MeminterestEntity {

    // 회원관심사코드
    @Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MEMINST_NO")
    private Long micode;

    // 관심사알람설정여부
    private Long mialert;

    // 일자
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date miregdate;

    // 회원테이블
    @ManyToOne
    @JoinColumn(name = "MEMBER_UID", referencedColumnName = "UID")
    private MemberEntity member;
    
    // 관심사테이블
    @ManyToOne
    @JoinColumn(name = "INTEREST_INCODE", referencedColumnName = "INCODE")
    private InterestEntity interest;

}
