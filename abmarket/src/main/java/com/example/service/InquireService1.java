package com.example.service;

import java.util.List;

import com.example.entity.InquireEntity;

import org.springframework.stereotype.Service;

@Service
public interface InquireService1 {

    // 문의글 작성하기
    public int insertInquire(InquireEntity inquire);

    // 문의글 조회 (1개)
    public InquireEntity selectOneInquire(long inqno);

    // 문의글 수정
    public int updateInquire(InquireEntity Inquire);

    // 문의글 삭제
    public int deleteInquire(long inqno);

    // 문의글 목록 (페이지, 검색)
    public List<InquireEntity> selectListPageSearchInquire(int page, String text);

    // 페이지네이션
    public long countSearchInquire(String text);

    // 자주 묻는 질문 FAQ??
}
