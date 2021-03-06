package com.example.service;

import com.example.entity.MemberEntity;
import com.example.entity.RankEntity;

import org.springframework.stereotype.Service;

@Service
public interface MemberService1 {

    // 회원가입
    public int insertMember(MemberEntity member);

    // 중복확인
    public MemberEntity selectMemberOne(String uid);

    // 회원수정
    public int updateMemberOne(MemberEntity member);

    // 회원탈퇴
    public int deleteMemberOne(String uid);

    // 랭크 집어넣기
    public int insertRank(MemberEntity member, RankEntity rank);
}
