package com.example.service;

import java.util.List;

import com.example.entity.AlertEntity;
import com.example.entity.BoardAndWriter;
import com.example.entity.BoardEntity;
import com.example.entity.ChatViewEntity;
import com.example.entity.ChatroomEntity;
import com.example.entity.CommEntity;
import com.example.entity.InquireEntity;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


// 알림 서비스
@Service
public interface AlertService3 {
    
    // 알림 1개 등록
    public int insertAlert(AlertEntity alert);
    
    // 알림 1개 삭제
    public int deleteAlert(Long alno);

    // 알림 일괄 삭제 -> 다시 확인**************
    public long deleteAlertBatch(List<Long> alno);

    // 알림 1개 조회(상세) 후 1->0으로 수정하기
    public AlertEntity selectOneAlert(Long alno);

    // 알림 전체 목록 조회(페이지) (검색X)
    // 읽기 여부 상관X
    public List<AlertEntity> selectAlertAllList(Pageable page, String uid);

    // 읽지 않은(1) 알림 목록 조회(페이지) (검색X)
    public List<AlertEntity> selectUnReadAlertList(Pageable page, String uid, Long alread);

    // 읽지 않은(1) 알림 개수 호출
    public Long alertUnReadCount(Long alread, String uid);

    // 알림 종류 확인(댓글, 대댓글, 후기 등 확인)
    public AlertEntity alertTypeChk(Long alno, Long altype);

    // 알림 읽기 여부 수정 (alread)
    public int updateAlread(AlertEntity alertEntity);
    
    // 문의 답변 알림
    public void sendAnswerAlert(InquireEntity inquireEnt, AlertEntity alertEnt);

    // 후기 작성 완료 알림 (판매자에게 알림)
    public void sendReviewAlert(ChatroomEntity chatRoonEnt, AlertEntity alertEnt);

    // 댓글 알림
    public void sendCommAlert(BoardEntity boardEnt, AlertEntity alertEnt);

    // 대댓글 알림
    public void sendRecommentAlert(CommEntity commEnt, AlertEntity alertEnt);

    // 등급 알림
    public void sendRankUpAlert(ChatViewEntity chatViewEnt, AlertEntity alertEnt);

    // 후기 작성 여부 알림 (구매자에게 알림)
    public void sendInsertReviewAlert(ChatViewEntity chatViewEnt, AlertEntity alertEnt);

    // 체크한 관심사 새 글 알림
    public void sendInterestAlert(BoardAndWriter bodAndWri, AlertEntity alertEnt);
    
}
