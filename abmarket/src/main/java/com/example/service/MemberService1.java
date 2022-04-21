package com.example.service;

import com.example.entity.MemberEntity;

import org.springframework.stereotype.Service;

@Service
public interface MemberService1 {

    // 회원가입
    public MemberEntity insertMember(MemberEntity member);

    // 회원조회
    public MemberEntity selectMemberOne(String uid);

    // 회원수정
    public int updateMemberOne(MemberEntity member);

    // 회원탈퇴
    public int deleteMemberOne(String uid);

    // 로그인
    public MemberEntity selectLogin(MemberEntity member);

}
