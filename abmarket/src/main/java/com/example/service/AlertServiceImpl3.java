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
        System.out.println("알림저장서비스impl여기==========");
        System.out.println(alert);
        //null
        //AlertEntity(alno=null, almessage=null, alread=1, altype=null, alregdate=null, alreaddate=null, alurl=null, member=null)
        try {
            // 설정된 타입으로 유효성 검사 
            // 알림 db에 넣을 컬럼 설정 추가
            if(alert != null) {
                AlertEntity aEntity = new AlertEntity();
                alert.setAlmessage(aEntity.getAlmessage());
                alRepository3.save(alert);
                System.out.println(alert.getAlno());
                return 0;
            }
            return 1;
        } catch (Exception e) {
            e.getStackTrace();
            System.out.println("알림저장서비스impl에러======="+e);
            return -1;
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

    @Autowired
    InquireRepository3 inqRepository3;

    @Override
    public void sendAnswerAlert(InquireEntity inquire) {
        System.out.println("실시간알림여기===================");
        System.out.println(inquire); // 답변 적은 해당 문의글 나옴
        //InquireEntity(inqno=4, inqtitle=null, inqcontent=null, inqregdate=null, inqtype=0, inqselect=1, inqfaqselect=1, member=null, answerList=[])
        
        // 해당 글 번호 호출
        Long inq = inquire.getInqno();
        System.out.println(inq);

        // 해당 회원 호출
        InquireEntity iEntity = inqRepository3.getById(inq);
        System.out.println(iEntity.getMember().getUid());

        String userid = iEntity.getMember().getUid();
        if(sseEmitters.containsKey(userid)) {
            SseEmitter sseEmitter = sseEmitters.get(userid);
            try {
                // 알림 전송
                //.data( commentUsername + "님이 작성하신 피드에 댓글을 달았습니다 " + ": "+ contents));
                sseEmitter.send(SseEmitter.event().name("sendAnswerAlert").data("문의답변확인하세여"));
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("알람서비스에러====="+e);
                sseEmitters.remove(userid);
            }
        }
    }

}
