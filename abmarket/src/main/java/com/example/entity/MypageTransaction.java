package com.example.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.Immutable;

import lombok.Data;

@Entity
@Data
@Immutable
@Table(name = "MYPAGETRANSACTION")
public class MypageTransaction {

    @Id
    Long crno;

    Long bno;

    String clickperson;

    String chstate;

    String baddress;

    String bcontent;

    String btitle;

    Long bcount;

    Date benddate;

    Long bdone;

    Long bprice;

    Long brole;

    Long reviewRevno;

}
