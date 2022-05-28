package com.example.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.Immutable;

import lombok.Data;

@Entity
@Data
@Immutable
@Table(name = "MEMINTANDBODANDBODINT")
public class MemIntAndBodAndBodInt {
    
    @Id
    Long boino;

    Long mialert;
    
    Long interestIncode;
    
    String memberUid;

    Long bno;

    Long brole;

}
