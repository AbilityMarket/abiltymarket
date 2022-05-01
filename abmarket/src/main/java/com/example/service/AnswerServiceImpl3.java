package com.example.service;

import java.util.List;

import com.example.entity.AnswerEntity;
import com.example.repository.AnswerRepository3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnswerServiceImpl3 implements AnswerService3 {

    @Autowired
    AnswerRepository3 ansRepository3;

    // 답변 1개 삭제
    @Override
    public int deleteOneAnswer(Long anno) {
        try {
            ansRepository3.deleteById(anno);
            return 1;
        } catch (Exception e) {
            e.getStackTrace();
            return 0;
        }
    }

    // 답변 일괄 삭제
    @Override
    public long deleteAnswerList() {
        return 0;
    }

    // 답변 1개 등록
    @Override
    public int insertAnswer(AnswerEntity answer) {
        try {
            ansRepository3.save(answer);
            return 1;
        } catch (Exception e) {
            e.getStackTrace();
            return 0;
        }
    }

    // 답변 1개 조회
    @Override
    public AnswerEntity selectOneAnswer(Long anno) {
        try {
            return ansRepository3.findById(anno).orElse(null);
        } catch (Exception e) {
            e.getStackTrace();
            return null;
        }
    }

    // 답변 전체 조회
    @Override
    public List<AnswerEntity> selectAnswerList() {
        return null;
    }

    // 답변 1개 수정
    @Override
    public int updateOneAnswer(AnswerEntity answer) {
        try {
            ansRepository3.save(answer);
            return 1;
        } catch (Exception e) {
            e.getStackTrace();
            return 0;
        }
    }

}
