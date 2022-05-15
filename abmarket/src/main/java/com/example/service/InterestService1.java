package com.example.service;

import java.util.List;

import com.example.entity.InterestEntity;

import org.springframework.stereotype.Service;

@Service
public interface InterestService1 {

    // 관심사 등록하기(관리자)
    public int insertInterest(InterestEntity interest);

    // 관심사 조회하기
    public InterestEntity selectOneInterest(long incode);

    // 관심사 수정하기(관리자)
    public int updateInterest(InterestEntity interest);

    // 관심사 삭제하기(관리자)
    public int deleteInterest(long incode);

    // 추가하기[일괄]
    public int insertInterestBatch(List<InterestEntity> list);

    // 수정시 해당하는 항목만 조회하기
    public InterestEntity selectInterestEntity(Long incode);

    // 수정하기[일괄]
    public int updateInterestBatch(List<InterestEntity> list);

    // 삭제하기[부분]
    public int deleteInterestBatch(Long[] incode);
}
