package com.example.service;

import java.util.List;

import com.example.entity.MeminterestEntity;

import org.springframework.stereotype.Service;

@Service
public interface MemInterestService1 {

    // 관심사 on, off
    public int updatealert(MeminterestEntity mialert);

    // 관심사 알람 설정 유무
    public int chkalertinterest(String uid, long mialert);

    // 회원 관심사 등록
    public int insertinterest(MeminterestEntity mialert);

    // 회원 관심사 등록 해제
    public int deleteinterest(String uid, long incode);

    // 해당 회원 관심사 조회
    public List<MeminterestEntity> selectListMemInt(String userid);

}
