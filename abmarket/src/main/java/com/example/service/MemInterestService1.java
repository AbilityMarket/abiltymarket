package com.example.service;

import com.example.entity.MeminterestEntity;

import org.springframework.stereotype.Service;

@Service
public interface MemInterestService1 {

    // 관심사 on
    public int insertalert(MeminterestEntity mialert);

    // 관심사 off
    public int deletealert(long micode);

    // 관심사 알람 설정 유무
    public int chkalertinterest(String uid, long mialert);

    // 회원 관심사 등록
    public int insertinterest(MeminterestEntity mialert);

    // 회원 관심사 등록 해제
    public int deleteinterest(String uid, long incode);

}
