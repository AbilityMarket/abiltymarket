package com.example.service;

import java.util.List;

import com.example.entity.InterestEntity;
import com.example.repository.InterestRepository1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InterestServiceImpl1 implements InterestService1 {

    @Autowired
    InterestRepository1 intRepository1;

    // 관심사 등록하기
    @Override
    public int insertInterest(InterestEntity interest) {
        try {
            intRepository1.save(interest);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 관심사 조회하기
    @Override
    public InterestEntity selectOneInterest(long incode) {
        try {
            return intRepository1.findById(incode).orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 관심사 수정하기
    @Override
    public int updateInterest(InterestEntity interest) {
        try {
            intRepository1.save(interest);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 관심사 삭제하기
    @Override
    public int deleteInterest(long incode) {
        try {
            intRepository1.deleteById(incode);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 이미지 등록
    @Override
    public long insertInterestImage(InterestEntity interestimage) {
        try {
            intRepository1.save(interestimage);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 이미지 조회(1개)
    @Override
    public InterestEntity selectOneInterestImage(long incode) {
        try {
            return intRepository1.findById(incode).orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 이미지 수정
    @Override
    public int updateInterestImage(InterestEntity interestimage) {
        try {
            intRepository1.save(interestimage);
            return 1;

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 이미지 삭제
    @Override
    public int deleteInterestImage(long incode) {
        try {
            intRepository1.deleteById(incode);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 이미지 조회(여러개)
    @Override
    public List<Long> selectListInterestImage(long incode) {
        try {

        } catch (Exception e) {
        }
        return null;
    }

}
