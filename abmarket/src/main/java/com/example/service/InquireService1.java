package com.example.service;

import java.util.List;

import com.example.entity.InquireEntity;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface InquireService1 {
    
    // 자주 묻는 질문 FAQ 형태는 질문과 답변이 함께 나타남

    // 1개 등록
    public int insertOne(InquireEntity inquire);

    // 1개 조회
    public InquireEntity selectOne(long inqno);

    // 1개 수정
    public int updateOne(InquireEntity inquire);

    // 1개 삭제
    public int deleteOne(long inqno);

    // 전체 목록 조회 (페이지, 검색)
    public List<InquireEntity> selectListPageSearchInquire(Pageable page, String text, long select);

    // 페이지네이션
    public long countSearch(String text);

}
