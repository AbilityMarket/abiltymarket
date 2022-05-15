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

    // 관심사 등록하기(관리자)
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

    // 관심사 수정하기(관리자)
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

    // 관심사 삭제하기(관리자)
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

    // 추가하기[일괄]
    @Override
    public int insertInterestBatch(List<InterestEntity> list) {
        try {
            intRepository1.saveAll(list);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 수정시 해당하는 항목만 조회하기
    @Override
    public InterestEntity selectInterestEntity(Long incode) {
        try {
            return intRepository1.findById(incode).orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 수정하기[일괄]
    @Override
    public int updateInterestBatch(List<InterestEntity> list) {
        try {
            intRepository1.saveAll(list);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 삭제하기[부분]
    @Override
    public int deleteInterestBatch(Long[] incode) {
        try {
            for (Long interest : incode) {
                intRepository1.deleteById(interest);
            }
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
