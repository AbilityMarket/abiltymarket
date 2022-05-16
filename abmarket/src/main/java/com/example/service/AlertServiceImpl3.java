package com.example.service;

import java.time.LocalDateTime;
import java.util.List;

import com.example.entity.AlertEntity;
import com.example.entity.BoardEntity;
import com.example.entity.CommEntity;
import com.example.entity.InquireEntity;
import com.example.entity.Reviewview;
import com.example.repository.AlertRepository3;
import com.example.repository.BoardRepository1;
import com.example.repository.CommRepository2;
import com.example.repository.InquireRepository3;
import com.example.repository.MemberRespository2;

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
    
    @Autowired MemberRespository2 memRepository2;

    // 알림 1개 등록
    @Override
    public int insertAlert(AlertEntity alertEnt) {
        System.out.println("알림등록저장서비스impl여기==========");
        System.out.println(alertEnt);
        //null
        //AlertEntity(alno=null, almessage=null, alread=1, altype=null, alregdate=null, alreaddate=null, alurl=null, member=null)
        try {
            // 설정된 타입으로 유효성 검사 
            // 알림 db에 넣을 컬럼 설정 추가
            if(alertEnt.getAltype() == 1L) {
                alertEnt.setAlmessage("문의답변을확인하세요");
                alRepository3.save(alertEnt);
                //System.out.println(alertEnt.getAlmessage());
            }
            // 후기는 다시 확인
            else if(alertEnt.getAltype() == 2L) {
                alertEnt.setAlmessage("후기를확인하세요");
                alRepository3.save(alertEnt);
                //System.out.println(alertEnt.getAlmessage());
            }
            else if(alertEnt.getAltype() == 3L) {
                alertEnt.setAlmessage("댓글을확인하세요");
                alRepository3.save(alertEnt);
                //System.out.println(alertEnt.getAlmessage());
            }
            else if(alertEnt.getAltype() == 4L) {
                alertEnt.setAlmessage("대댓글을확인하세요");
                alRepository3.save(alertEnt);
                //System.out.println(alertEnt.getAlmessage());
            }
            return 0;
        } catch (Exception e) {
            e.getStackTrace();
            System.out.println("알림저장서비스impl에러======="+e);
            return -1;
        }
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

    // 알림 1(읽지않음) -> 0(읽음) 수정
    @Override
    public int updateAlread(AlertEntity alertEntity) {
        try {
            System.out.println("읽은여부수정여기====="+alertEntity);
            
            // DB alread 체크
            AlertEntity alertEnt2 = alRepository3.findByAlreadAndMember_uidAndAltypeAndAlurlAndAlno
            (alertEntity.getAlread(), alertEntity.getMember().getUid(), alertEntity.getAltype(), alertEntity.getAlurl(), alertEntity.getAlno());
            // 1->0 수정
            if(alertEntity.getAlread() == 1L) {
                alertEnt2.setAlread(0L);
                System.out.println("실시간 알림 호출 후 1L => " + alertEntity.getAlread());

                // 알림 확인 일자 추가
                LocalDateTime readNow = LocalDateTime.now();
                alertEnt2.setAlreaddate(readNow);
                //System.out.println(readNow);

                alRepository3.save(alertEntity);
                System.out.println("읽음");
            }
            return 1;
        } catch (Exception e) {
            e.getStackTrace();
            return 0;
        }
    }

    @Autowired
    InquireRepository3 inqRepository3;

    @Autowired
    BoardRepository1 boardRepository1;

    @Autowired
    CommRepository2 commRepository2;

    @Autowired
    AlertService3 alertService3;

    // 문의 답변 알림
    @Override
    public void sendAnswerAlert(InquireEntity inquireEnt, AlertEntity alertEnt) {
        System.out.println("문의답변알림서비스===" + inquireEnt); // 답변 적은 해당 문의글 나옴
        //InquireEntity(inqno=4, inqtitle=null, inqcontent=null, inqregdate=null, inqtype=0, inqselect=1, inqfaqselect=1, member=null, answerList=[])
        
        // 해당 글 번호 호출
        Long inq = inquireEnt.getInqno();
        //System.out.println(inq);

        // 해당 회원 호출
        InquireEntity iEntity = inqRepository3.getById(inq);
        //System.out.println(iEntity.getMember().getUid());

        String userid = iEntity.getMember().getUid();
        if(sseEmitters.containsKey(userid)) {
            SseEmitter sseEmitter = sseEmitters.get(userid);
            try {
                // 알림 전송
                //.data(userid + "님이 작성하신 피드에 댓글을 달았습니다 " + ": "+ contents));
                sseEmitter.send(SseEmitter.event().name("sendAnswerAlert").data(userid + "님이 작성하신 문의글에 답변을 확인하세요"));
                // 알림 alread (1->0) 읽음으로 바꾸기 호출
                alertService3.updateAlread(alertEnt);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("알람서비스에러====="+e);
                sseEmitters.remove(userid);
            }
        }
    }

    // 후기 알림 (판매자 작성자에게 알림or채팅방에 있는 작성자에게 알림)
    // *** 채팅 구현 후 다시 설정 해야 됨 ***
    // 어느 엔티티에서 회원을 호출할지 정해야 됨
    @Override
    public void sendReviewAlert(Reviewview reviewview, AlertEntity alertEnt) {
        System.out.println("후기알림서비스===" + reviewview);
    }

    // 댓글 알림
    @Override
    public void sendCommAlert(BoardEntity boardEnt, AlertEntity alertEnt) {
        System.out.println("댓글알림서비스===" + boardEnt);
        //BoardEntity(bno=1, btitle=null, bcontent=null, bhit=1, bprice=null, bregdate=null, brole=null, bdone=null, bcount=null,
        //benddate=null, baddress=null, bimage=null, bimagesize=0, bimagetype=null, bimagename=null, member=null)
        Long bod = boardEnt.getBno();
        //System.out.println(bod); //1
        BoardEntity bEntity = boardRepository1.getById(bod);
        //System.out.println(bEntity.getMember().getUid()); //aa
        String userid = bEntity.getMember().getUid();
        if(sseEmitters.containsKey(userid)) {
            SseEmitter sseEmitter = sseEmitters.get(userid);
            try {
                sseEmitter.send(SseEmitter.event().name("sendCommAlert").data(userid + "님이 쓰신 글에 댓글이 추가되었어요!"));
                // 알림 alread (1->0) 읽음으로 바꾸기 호출
                alertService3.updateAlread(alertEnt);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("알람서비스에러====="+e);
                sseEmitters.remove(userid);
            }
        }
    }

    // 대댓글 알림
    @Override
    public void sendRecommentAlert(CommEntity commEnt, AlertEntity alertEnt) {
        System.out.println("대댓글알림서비스===" + commEnt);
        Long comm = commEnt.getCono();
        //System.out.println(comm);
        CommEntity cEntity = commRepository2.getById(comm);
        //System.out.println(cEntity.getMember().getUid());
        String userid = cEntity.getMember().getUid();
        if(sseEmitters.containsKey(userid)) {
            SseEmitter sseEmitter = sseEmitters.get(userid);
            try {
                //알림창에 url 추가 해보기
                //String url = "/ROOT/api/comm/insertRecomment?cono=" + comm;
                sseEmitter.send(SseEmitter.event().name("sendRecommentAlert").data(userid + "님이 쓰신 댓글에 대댓글이 추가되었어요!"));
                // 알림 alread (1->0) 읽음으로 바꾸기 호출
                alertService3.updateAlread(alertEnt);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("알람서비스에러====="+e);
                sseEmitters.remove(userid);
            }
        }
    }

}
