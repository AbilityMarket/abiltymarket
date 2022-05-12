package com.example.service;

import com.example.entity.MeminterestEntity;
import com.example.repository.MemInterestRepository1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemInterestServiceImpl1 implements MemInterestService1 {

    @Autowired
    MemInterestRepository1 memIntRepository1;

    // 관심사 알람 설정 on off
    @Override
    public int updatealert(MeminterestEntity mialert) {
        try {
            memIntRepository1.save(mialert);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 관심사 알람 설정 유무 확인
    @Override
    public int chkalertinterest(String uid, long mialert) {
        try {
            return memIntRepository1.countByMember_uidAndMialert(uid, mialert);

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    // 회원 관심사 등록
    @Override
    public int insertinterest(MeminterestEntity meminterest) {
        try {
            memIntRepository1.save(meminterest);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 회원 관심사 등록 해제
    @Override
    public int deleteinterest(String uid, long incode) {
        try {
            memIntRepository1.deleteByMember_uidAndInterest_incode(uid, incode);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

}
