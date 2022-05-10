package com.example.service;

import java.util.List;

import com.example.entity.AlertEntity;
import com.example.entity.InquireEntity;
import com.example.repository.AlertRepository3;
import com.example.repository.InquireRepository3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import static com.example.restcontroller.AlertRestController3.sseEmitters;

@Service
public class AlertServiceImpl3 implements AlertService3 {

    @Autowired AlertRepository3 alRepository3;

    @Autowired InquireRepository3 inquireRepository3;

    // 알림 1개 삭제
    @Override
    public int deleteAlert(Long alno) {
        try {
            alRepository3.deleteById(alno);
            return 1;
        } catch (Exception e) {
            e.getStackTrace();
            return 0;
        }
    }

    // 알림 일괄 삭제
    @Override
    public long deleteAlertBatch(List<Long> alno) {
        try {
            return 1;
        } catch (Exception e) {
            e.getStackTrace();
            return 0;
        }
    }

    // 알림 1개 등록
    @Override
    public int insertAlert(AlertEntity alert) {
        try {
            alRepository3.save(alert);
            return 1;
        } catch (Exception e) {
            e.getStackTrace();
            //System.out.println(e);
            return 0;
        }
    }

    // 알림 일괄 등록
    @Override
    public long insertAlertBatch(List<AlertEntity> list) {
        return 0;
    }

    // 알림 전체 목록 조회(페이지)
    @Override
    public List<AlertEntity> selectAlertList(Pageable page) {
        return null;
    }

    // 알림 1개 조회
    @Override
    public AlertEntity selectOneAlert(Long alno) {
        try {
            return alRepository3.findById(alno).orElse(null);
        } catch (Exception e) {
            e.getStackTrace();
            return null;
        }
    }
    
    // 알림 1개 수정
    @Override
    public int updateOneAlert(AlertEntity alert) {
        try {
            alRepository3.save(alert);
            return 1;
        } catch (Exception e) {
            e.getStackTrace();
            return 0;
        }
    }

    // 알림 일괄 수정
    @Override
    public long updateAlertBatch(List<AlertEntity> list) {
        return 0;
    }

    // 알림 종류 확인
    @Override
    public int alertTypeChk(Long alno) {
        try {
            return 1;
        } catch (Exception e) {
            e.getStackTrace();
            return 0;
        }
    }

    // 읽지 않은 알림 표시
    @Override
    public Long alertUnReadCount(Long alno) {
        try {
            Long alread = 1L;
            return alRepository3.countByAlreadAndAlno(alread, alno);
        } catch (Exception e) {
            e.getStackTrace();
            return null;
        }
    }

    // 읽은 알림 수 호출
    @Override
    public int alertReadUpdate(Long alno) {
        return 0;
    }

    
    public void sendAnswerAlert(InquireEntity inquire) {

        String userid = inquire.getMember().getUid();

        if(sseEmitters.containsKey(userid)) {
            SseEmitter sseEmitter = sseEmitters.get(userid);
            try {
                sseEmitter.send(SseEmitter.event().name("addAnswer").data("답변!!!"));
            } catch (Exception e) {
                e.printStackTrace();
                sseEmitters.remove(userid);
            }
        }
    }
    
}
