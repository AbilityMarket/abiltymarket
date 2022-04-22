package com.example.service;

import java.util.List;

import com.example.entity.AlertEntity;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface AlertService3 {
    
    // 알림 1개 등록
    public int insertAlert(AlertEntity alert);

    // 알림 일괄 등록
    public long insertAlertBatch(List<AlertEntity> list);
    
    // 알림 1개 삭제
    public int deleteAlert(AlertEntity alno);

    // 알림 일괄 삭제
    public long deleteAlertBatch(List<Long> alno);
    
    // 알림 1개 수정
    public int updateAlert(AlertEntity alert);

    // 알림 일괄 수정(다시 확인)
    public long updateAlertBatch(List<AlertEntity> list);

    // 알림 일괄 수정 => 해당 목록 가져오기(다시 확인)
    public long updateAlertBatchListIn(List<Long> alno);

    // 알림 1개 조회
    public int selectAlertOne(AlertEntity alno);

    // 알림 전체 목록 조회(페이지) (검색X) (다시 확인)
    public List<AlertEntity> selectAlertList(Pageable page);

}
