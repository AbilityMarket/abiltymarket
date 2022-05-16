package com.example.service;

import java.util.List;

import com.example.entity.AlertEntity;
import com.example.entity.BoardEntity;
import com.example.entity.CommEntity;
import com.example.entity.InquireEntity;
import com.example.entity.Reviewview;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


// 알림 서비스
@Service
public interface AlertService3 {
    
    // 알림 1개 등록
    public int insertAlert(AlertEntity alert);
    
    // 알림 1개 삭제
    public int deleteAlert(Long alno);

    // 알림 일괄 삭제
    public long deleteAlertBatch(List<Long> alno);

    // 알림 1개 조회
    public AlertEntity selectOneAlert(Long alno);

    // 알림 전체 목록 조회 불러오기(페이지) (검색X)
    public List<AlertEntity> selectAlertList(Pageable page);

    // 읽지 않은 알림 수 호출
    public Long alertUnReadCount (Long alno);

    // 읽은 알림 수 호출
    public int alertReadUpdate(Long alno);

    // 알림 종류 확인
    public int alertTypeChk(Long alno);

    // 알림 읽기 여부 수정 (alread)
    public int updateAlread(AlertEntity alertEntity);
    
    // 문의 답변 알림
    public void sendAnswerAlert(InquireEntity inquireEnt, AlertEntity alertEnt);

    // 후기 알림
    // *** 채팅 구현 후 다시 설정 해야 됨 ***
    // 어느 엔티티에서 회원을 호출할지 정해야 됨
    public void sendReviewAlert(Reviewview reviewview, AlertEntity alertEnt);

    // 댓글 알림
    public void sendCommAlert(BoardEntity boardEnt, AlertEntity alertEnt);

    // 대댓글 알림
    public void sendRecommentAlert(CommEntity commEnt, AlertEntity alertEnt);
}
