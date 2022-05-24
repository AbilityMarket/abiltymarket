package com.example.service;

import java.util.HashMap;

import com.example.entity.MemberAddrEntity;

import org.springframework.stereotype.Service;

@Service
public interface MemAddrService3 {

    // 주소 api 호출
    public String getKakaoApiFromMemAddr(String roadFullAddr);

    // 주소 api Map으로 변환
    public HashMap<String, String> getXYMapFromJsonStr(String jsonString);

    // 주소 1개 등록
    public int insertMemAddr(MemberAddrEntity memAddrEnt);

    // 주소 1개 수정
    public int updateMemAddr(MemberAddrEntity memAddrEnt);

    // 주소 일괄 수정


    // 주소 1개 조회


    // 주소 1개 삭제


    // 주소 일괄 삭제


    // 등록한 주소 전체 조회
    

}
