package com.example.service;

import java.util.List;

import com.example.entity.MemIntAndBodAndBodInt;
import com.example.entity.MeminterestEntity;
import com.example.repository.MemIntAndBodAndBodIntRepository3;
import com.example.repository.MemInterestRepository1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemInterestServiceImpl1 implements MemInterestService1 {

    @Autowired
    MemInterestRepository1 memIntRepository1;

    @Autowired
    MemIntAndBodAndBodIntRepository3 memIntAndBodAndBodIntRepository3;

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

    // 관심사별 회원 조회
    @Override
    public List<MeminterestEntity> selectListInt(List<Long> incode) {
        try {
            return memIntRepository1.findAllByInterest_incodeIn(incode);
        } catch (Exception e) {
            return null;
        }
    }

    // 회원별 관심사 조회
    @Override
    public List<MeminterestEntity> selectListMemInt(String userid) {
        try {
            return memIntRepository1.findByMember_uid(userid);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 회원별 관심사 중 해당되는 게시판 전체 조회 (뷰 생성)
    @Override
    public List<MemIntAndBodAndBodInt> memIntChkBod(String userid) {
        try {
            return memIntAndBodAndBodIntRepository3.findByMemberUid(userid);
        } catch (Exception e) {
            return null;
        }
    }

    // 해당 회원 관심사에 포함 된 게시글 구매 판매 조회
    @Override
    public List<MemIntAndBodAndBodInt> memIntChkBrole(String userid, Long brole) {
        try {
            return memIntAndBodAndBodIntRepository3.findByMemberUidAndBrole(userid, brole);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public MeminterestEntity selectListInt2(Long incode) {
        try {
            return memIntRepository1.findByInterest_incode(incode);
        } catch (Exception e) {
            return null;
        }

    }

}
