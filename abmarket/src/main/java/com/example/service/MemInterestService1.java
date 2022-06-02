package com.example.service;

import java.util.List;

import com.example.entity.MemIntAndBodAndBodInt;
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

    // 관심사별 회원 조회
    public MeminterestEntity selectListInt2(Long incode);

    public List<MeminterestEntity> selectListInt(List<Long> incode);

    // 회원별 관심사 조회
    public List<MeminterestEntity> selectListMemInt(String userid);

    // 회원별 관심사 중 해당되는 게시판 전체 조회 (뷰 생성)
    public List<MemIntAndBodAndBodInt> memIntChkBod(String userid);

    // 해당 회원 관심사에 포함 된 게시글 구매 판매 조회
    public List<MemIntAndBodAndBodInt> memIntChkBrole(String userid, Long brole);

}
