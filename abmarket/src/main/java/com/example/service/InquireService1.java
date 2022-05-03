package com.example.service;

import java.util.List;

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
    public int updateOneInquire(InquireEntity inquire);

    // 문의글 삭제
    public int deleteOneInquire(long inqno);

    // 문의글 전체 목록 조회 (페이지, 검색)
    // ======== 전체 목록에서 faq 제외 해야 됨 다시 수정 ==========
    public List<InquireEntity> selectListPageSearchInquire(Pageable page, String text);

    // 페이지네이션
    // ========== 이것도 faq 제외 해야 됨 다시 수정 ===============
    public long countSearchInquire(String text);

    // 자주 묻는 질문 FAQ 형태는 질문과 답변이 함께 나타남
    // FAQ 등록(관리자)
    public int insertOneFaq (InquireEntity inquirefaq);

    // FAQ 삭제(관리자)
    public int deleteOneFaq(long inqno);

    // FAQ 전체 조회(페이지, 검색)
    // ======= 문의글 제외 해야 됨 ==============


    // FAQ 1개 조회(토큰)


    // FAQ 수정(관리자)

    
}
