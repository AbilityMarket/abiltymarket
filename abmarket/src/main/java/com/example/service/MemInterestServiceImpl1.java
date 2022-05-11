package com.example.service;

import java.util.List;

import com.example.entity.MeminterestEntity;
import com.example.repository.MemInterestRepository1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemInterestServiceImpl1 implements MemInterestService1 {

    @Autowired
    MemInterestRepository1 memIntRepository1;

    // 관심사 on
    @Override
    public int insertalert(MeminterestEntity mialert) {
        try {
            memIntRepository1.save(mialert);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 관심사 off
    @Override
    public int deletealert(long micode) {
        try {
            memIntRepository1.deleteById(micode);
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
            List<MeminterestEntity> list = memIntRepository1.findByMialertAndMember_uid(mialert, uid);
            if (list != null) {
                return 1;
            }
            return 0;
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
