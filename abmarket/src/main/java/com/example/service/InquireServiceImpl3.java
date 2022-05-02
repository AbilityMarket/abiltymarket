package com.example.service;

import java.util.List;
import java.util.Map;

import com.example.entity.InquireEntity;
import com.example.repository.InquireRepository3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class InquireServiceImpl3 implements InquireService1 {

    @Autowired InquireRepository3 inqRepository3;

    @Override
    public long countSearchInquire(Map<String, Object> map) {
        return 0;
    }

    // 문의글 삭제
    @Override
    public int deleteOneInquire(long inqno) {
        try {
            inqRepository3.deleteById(inqno);
            return 1;
        } catch (Exception e) {
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
            return 0;
        }
    }

    // 문의글 전체 목록 조회 (페이지, 검색)
    @Override
    public List<InquireEntity> selectListPageSearchInquire(Pageable page, String text) {
        return null;
    }

    // 문의글 조회 (1개)
    @Override
    public InquireEntity selectOneInquire(long inqno) {
        return null;
    }

    // 문의글 수정
    @Override
    public int updateOneInquire(InquireEntity Inquire) {
        return 0;
    }
    
}
