package com.example.service;

import java.util.List;
import java.util.Map;

import com.example.entity.InquireEntity;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface InquireService1 {

    // 문의글 작성하기
    public int insertInquire(InquireEntity inquire);

    // 문의글 조회 (1개)
    public InquireEntity selectOneInquire(long inqno);

    // 문의글 수정
    public int updateOneInquire(InquireEntity Inquire);

    // 문의글 삭제
    public int deleteOneInquire(long inqno);

    // 문의글 전체 목록 조회 (페이지, 검색)
    public List<InquireEntity> selectListPageSearchInquire(Pageable page, String text);

    // 페이지네이션
    public long countSearchInquire(Map<String, Object> map);

    // 자주 묻는 질문 FAQ 형태는 질문과 답변이 함께 나타남 (목록은 검색과 페이지네이션 설정)
}
