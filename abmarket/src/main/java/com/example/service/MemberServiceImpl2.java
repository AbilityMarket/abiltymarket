package com.example.service;

import com.example.entity.MemberEntity;
import com.example.entity.RankEntity;
import com.example.entity.RrrankEntity;
import com.example.repository.MemberRespository2;
import com.example.repository.RrrankRepository2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl2 implements MemberService1 {

    @Autowired
    MemberRespository2 mRespository2;

    @Autowired
    RrrankRepository2 rrrankRepository2;

    // 회원가입
    @Override
    public int insertMember(MemberEntity member) {
        try {
            // db에 있는지 확인하기
            MemberEntity member1 = mRespository2.findById(member.getUid()).orElse(null);
            if (member1 == null) {
                mRespository2.save(member);
                return 1;
            }
            return 0;

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    // 회원조회(중복확인)
    @Override
    public MemberEntity selectMemberOne(String uid) {
        try {
            // db에 있는지 확인하기
            MemberEntity member = mRespository2.findById(uid).orElse(null);
            return member;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    // 회원수정
    @Override
    public int updateMemberOne(MemberEntity member) {
        try {

        } catch (Exception e) {

        }
        return 0;
    }

    // 회원삭제
    @Override
    public int deleteMemberOne(String uid) {

        return 0;
    }

    // 등급넣기
    @Override
    public int insertRank(MemberEntity member, RankEntity rank) {
        try {
            RrrankEntity rrrank = new RrrankEntity();
            rrrank.setMember(member);
            rrrank.setRank(rank);
            rrrankRepository2.save(rrrank);
            if (member != null && rank != null) {
                return 1;
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
}
