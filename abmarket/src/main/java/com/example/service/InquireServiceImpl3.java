package com.example.service;

import java.util.List;

import com.example.entity.AnswerEntity;
import com.example.entity.InquireEntity;
import com.example.repository.InquireRepository3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class InquireServiceImpl3 implements InquireService1 {

    @Autowired InquireRepository3 inqRepository3;

    // 검색 결과 갯수 (저장소에서 따로 설정)
    @Override
    public long countSearch(String text) {
        try {
            inqRepository3.countByInqtitleContaining(text);
            return 1;
        } catch (Exception e) {
            e.getStackTrace();
            return 0;
        }
    }

    // 1개 삭제
    @Override
    public int deleteOne(long inqno) {
        try {
            inqRepository3.deleteById(inqno);
            return 1;
        } catch (Exception e) {
            e.getStackTrace();
            return 0;
        }
    }

    // 1개 등록
    @Override
    public int insertOne(InquireEntity inquire) {
        try {
            inqRepository3.save(inquire);
            return 1;
        } catch (Exception e) {
            e.getStackTrace();
            return 0;
        }
    }

    // 전체 목록 조회 (저장소에서 따로 설정)
    @Override
    public List<InquireEntity> selectListPageSearchInquire(Pageable page, String text, long select) {
        try {
            return inqRepository3.findByInqfaqselectAndInqtitleContainingOrderByInqnoDesc(select, page, text);
        } catch (Exception e) {
            e.getStackTrace();
            return null;
        }
    }

    // 1개 조회
    @Override
    public InquireEntity selectOne(long inqno) {
        try {
            return inqRepository3.findById(inqno).orElse(null);
        } catch (Exception e) {
            e.getStackTrace();
            return null;
        }
    }

    // 1개 수정
    @Override
    public int updateOne(InquireEntity inquire) {
        try {
            inqRepository3.save(inquire);
            return 1;
        } catch (Exception e) {
            e.getStackTrace();
            return 0;
        }
    }

    public void addAnswer(AnswerEntity answer) {
        
    }


    @Override
    public InquireEntity addAnswer(Long inqno, String uid, AnswerEntity answer) {

        InquireEntity inquirentity = inqRepository3.findById(inqno).get();

        AnswerEntity answerentity = new AnswerEntity();
        answerentity.setInquire(inquirentity);

        return inquirentity;

    }
    
}
