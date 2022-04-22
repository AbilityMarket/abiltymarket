package com.example.service;

import java.util.List;

import com.example.entity.InterestEntity;

import org.springframework.stereotype.Service;

@Service
public interface InterestService1 {

    // 관심사 등록하기
    public int insertInterest(InterestEntity Interest);

    // 관심사 조회하기
    public InterestEntity selectInterest(long incode);

    // 관심사 수정하기
    public int updateInterest(InterestEntity Interest);

    // 관심사 삭제하기
    public int deleteInterest(long incode);

    // 이미지 등록
    public long insertInterestImage(List<InterestEntity> list);

    // 이미지 가져오기(1개)
    public InterestEntity selectOneInterestImage(long incode);

    // 이미지 수정
    public int updateInterestImage(InterestEntity interest);

    // 이미지 삭제
    public int deleteInterestImage(long incode);

    // 이미지 가져오기(여러개)
    public List<Long> selectListInterestImage(long incode);
}
