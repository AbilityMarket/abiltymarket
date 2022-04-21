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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "ABTIP")
@SequenceGenerator(name = "SEQ_ABTIP_NO", sequenceName = "SEQ_ABTIP_NO", allocationSize = 1, initialValue = 1)

// 팁 테이블
public class AbTipEntity {

    // 팁코드
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ABTIP_NO")
    private Long abtno;

    // 팁제목
    @Column(length = 200)
    private String abttitle;

    // 팁내용
    @Lob
    private String abtcontent;

    // 일자
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date abtregdate;

    // 회원테이블
    @ManyToOne
    @JoinColumn(name = "MEMBER_UID", referencedColumnName = "UID")
    private MemberEntity member;

    // 팁이미지테이블
    @OneToMany(mappedBy = "abtip")
    private List<AbTipImageEntity> abimageList = new ArrayList<>();

    @Lob
    private byte[] abimage;

    // 이미지크기
    private Long abimagesize = 0L;

    // 이미지타입
    @Column(length = 30)
    private String abimagetype;

    // 이미지명
    @Column(length = 250)
    private String abimagename;

    // 일자
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date abiregdate;
}
