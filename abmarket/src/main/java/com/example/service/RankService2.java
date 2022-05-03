package com.example.service;

import com.example.entity.RankEntity;

import org.springframework.stereotype.Service;

@Service
public interface RankService2 {
    // 회원등급 조회하기
    public RankEntity selectRank(String uid);
}
