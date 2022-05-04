package com.example.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.Immutable;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Entity
@Data
@Immutable // view 인경우 추가, 읽기만 가능 findBy만 된다.
@Table(name = "CHATVIEW")
public class ChatViewEntity {

    @Id
    Long crno;

    Long startMessage;

    String writer;

    String clickperson;

    long boardBno;

    String crreport;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    Date crregdate;

    Long reviewRevno;

    String chstate;

    // 종류
    Long btag;
}
