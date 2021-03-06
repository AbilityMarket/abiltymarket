package com.example.service;

import java.util.List;
import java.util.Map;

import com.example.entity.AbTipEntity;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface AbTipService3 {

    // 팁 등록
    public int insertAbTip(AbTipEntity abtip);

    // 팁 전체 목록 조회, 페이지네이션 & 검색어 추가 (페이지네이션, 검색)
    public List<AbTipEntity> selectListAbTip(Pageable page, String abttitle);

    // 팁 전체 갯수 구하기(페이지네이션, 검색)
    public long selectCountAbTip(Map<String, Object> map);

    // 팁 1개 조회(이미지 포함) , 수정 시 기존 데이터 사용
    public AbTipEntity selectOneAbTip(long abtno);
    
    // 팁 1개 삭제
    public int deleteOneAbTip(long abtno);

    // 팁 1개 수정
    public int updateOneAbTip(AbTipEntity abtip);

}
