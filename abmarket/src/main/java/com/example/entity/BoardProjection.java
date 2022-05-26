package com.example.entity;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;

public interface BoardProjection {

    Long getBno();

    String getBcontent();

    String getBtitle();

    Long getBprice();

    Long getBrole();

    Long getBcount();

    Date getBenddate();

    String getBaddress();

    @Value("#{target.member.uid}")
    String getMemberUid();
}
