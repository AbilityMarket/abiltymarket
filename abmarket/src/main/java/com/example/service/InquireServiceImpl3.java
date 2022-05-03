package com.example.service;

import java.util.List;

import com.example.entity.InquireEntity;
import com.example.repository.InquireRepository3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class InquireServiceImpl3 implements InquireService1 {

    @Autowired InquireRepository3 inqRepository3;

    // 문의글 검색 결과 갯수 (저장소에서 따로 설정)
    @Override
    public long countSearchInquire(String text) {
        try {
            inqRepository3.countByInqtitleContaining(text);
            return 1;
        } catch (Exception e) {
            e.getStackTrace();
            return 0;
        }
    }

    // 문의글 삭제
    @Override
    public int deleteOneInquire(long inqno) {
        try {
            inqRepository3.deleteById(inqno);
            return 1;
        } catch (Exception e) {
            e.getStackTrace();
            return 0;
        }
    }

    // 문의글 등록
    @Override
    public int insertInquire(InquireEntity inquire) {
        try {
            inqRepository3.save(inquire);
            return 1;
        } catch (Exception e) {
            e.getStackTrace();
            return 0;
        }
    }

    // 문의글 전체 목록 조회 (저장소에서 따로 설정)
    @Override
    public List<InquireEntity> selectListPageSearchInquire(Pageable page, String text) {
        try {
            return inqRepository3.findByInqtitleContainingOrderByInqnoDesc(page, text);
        } catch (Exception e) {
            e.getStackTrace();
            return null;
        }
    }

    // 문의글 조회 (1개)
    @Override
    public InquireEntity selectOneInquire(long inqno) {
        try {
            return inqRepository3.findById(inqno).orElse(null);
        } catch (Exception e) {
            e.getStackTrace();
            return null;
        }
    }

    // 문의글 수정
    @Override
    public int updateOneInquire(InquireEntity inquire) {
        try {
            inqRepository3.save(inquire);
            return 1;
        } catch (Exception e) {
            e.getStackTrace();
            return 0;
        }
    }

    // FAQ 등록 (관리자)
    @Override
    public int insertOneFaq(InquireEntity inquirefaq) {
        try {
            inqRepository3.save(inquirefaq);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    // FAQ 삭제 (관리자)
    @Override
    public int deleteOneFaq(long inqno) {
        try {
            inqRepository3.deleteById(inqno);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }
    
}
