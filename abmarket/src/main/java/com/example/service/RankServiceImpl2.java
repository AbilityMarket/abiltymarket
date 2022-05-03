package com.example.service;

import com.example.entity.MemberEntity;
import com.example.entity.RankEntity;
import com.example.entity.RrrankEntity;
import com.example.repository.RankRepository1;
import com.example.repository.RrrankRepository2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RankServiceImpl2 implements RankService2 {

    @Autowired
    RankRepository1 rankRepository1;

    @Autowired
    RrrankRepository2 rrrankRepository2;

    // 회원등급 조회하기
    @Override
    public RankEntity selectRank(String uid) {
        try {
            MemberEntity member = new MemberEntity();
            member.setUid(uid);

            RrrankEntity rrrank = rrrankRepository2.findByMember_uid(uid);
            System.out.println(rrrank);
            return rankRepository1.findById(rrrank.getRank().getRname()).orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
