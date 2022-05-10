package com.example.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import lombok.Data;

@Entity
@Data
@Immutable
@Table(name = "REVIEWVIEW")
public class Reviewview {

    @Id
    Long crono;

    long boardBno;

    String memberUid;

    Long chno;

    // 채팅상태
    String chstate;

    // Long reviewRevno;

    Long revno;

    // 평점
    Long revscore;

}
