package com.example.service;

import java.util.List;

import com.example.entity.AlertEntity;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


// 알림 서비스
@Service
public interface AlertService3 {
    
    // 알림 1개 등록
    public int insertAlert(AlertEntity alert);

    // 알림 일괄 등록
    public long insertAlertBatch(List<AlertEntity> list);
    
    // 알림 1개 삭제
    public int deleteAlert(Long alno);

    // 알림 일괄 삭제
    public long deleteAlertBatch(List<Long> alno);
    
    // 알림 1개 수정
    public int updateOneAlert(AlertEntity alert);

    // 알림 일괄 수정
    public long updateAlertBatch(List<AlertEntity> list);

    // 알림 1개 조회
    public AlertEntity selectOneAlert(Long alno);

    // 알림 전체 목록 조회 불러오기(페이지) (검색X)
    public List<AlertEntity> selectAlertList(Pageable page);

    // 알림 읽은 여부 확인 (기본값 false)
    public boolean alertReadChk (Long alno);

    // 읽지 않은 알림 수 출력
    public int selectUnReadCount(Long alno);

    // 알림 종류 확인
    public int alertTypeChk(Long alno);

    // 알림 클릭 시 이동할 주소

    


}
