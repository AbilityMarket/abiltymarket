package com.example.service;

import com.example.entity.MeminterestEntity;

import org.springframework.stereotype.Service;

@Service
public interface MemInterestService1 {

    // 회원 관심사 등록
    public int insertalert(MeminterestEntity mialert);

    // 회원 관심사 삭제
    public int deletealert(long micode);

}
