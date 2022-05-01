package com.example.repository;

import java.util.List;

import com.example.entity.AnswerEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository3 extends JpaRepository<AnswerEntity, Long> {
    
    // 원본 문의 글번호와 일치하는 답변 갯수
    // private InquireEntity inquire;
    List<AnswerEntity> findByInquire_inqnoOrderByAnnoDesc(long inqno);

}
