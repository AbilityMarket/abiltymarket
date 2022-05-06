package com.example.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.Immutable;

import lombok.Data;

@Entity
@Data
@Immutable // view 인경우 추가, 읽기만 가능 findBy만 된다.
@Table(name = "TRADERANK")
public class TradeRankView {
    @Id
    String clickperson;

    Long plus;
}
