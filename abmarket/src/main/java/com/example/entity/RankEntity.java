package com.example.entity;

import java.util.ArrayList;
import java.util.List;

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

import lombok.Data;

@Data
@Entity
@Table(name = "RANK")
@SequenceGenerator(name = "SEQ_RANK_NO", sequenceName = "SEQ_RANK_NO", allocationSize = 1, initialValue = 1)

// 등급 테이블
public class RankEntity {

    // 등급이름
    @Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_RANK_NO")
    @Column(length = 20)
    private String rname;

    // 등급이미지
    @Lob
    private byte[] rimage;

    // 등급이미지크기
    private Long rimagesize = 0L;

    // 등급이미지타입
    @Column(length = 30)
    private String rimagetype;

    // 이미지명
    @Column(length = 250)
    private String rimagename;

    // 등급설명
    @Column(length = 100)
    private String rcontent;
    
    // 등급매기기
    @JsonBackReference
    @OneToMany(mappedBy = "rank")
    private List<RrrankEntity> rrrankList = new ArrayList<>();
    
}
