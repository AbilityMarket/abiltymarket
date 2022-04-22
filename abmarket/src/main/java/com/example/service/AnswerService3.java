package com.example.service;

import java.util.List;

import com.example.entity.AnswerEntity;

import org.springframework.stereotype.Service;

// 문의 답변 서비스
@Service
public interface AnswerService3 {
    
    // 답변 1개 등록
    public int insertAnswer(AnswerEntity answer);

    // 답변 1개 삭제
    public int deleteAnswer(Long anno);

    // 답변 일괄 삭제 (다시 확인)
    public long deleteAnswerList();

    // 답변 1개 수정
    public int updateAnswer(AnswerEntity answer);
    
    // 답변 1개 조회
    public AnswerEntity selectAnswer(Long anno);

    // 답변 전체 조회 (다시 확인)
    public List<AnswerEntity> selectAnswerList();

}
