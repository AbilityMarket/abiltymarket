package com.example.service;

import java.time.LocalDateTime;
import java.util.List;

import com.example.entity.AlertEntity;
import com.example.entity.BoardEntity;
import com.example.entity.BoardInterest;
import com.example.entity.ChatViewEntity;
import com.example.entity.ChatroomEntity;
import com.example.entity.CommEntity;
import com.example.entity.InquireEntity;
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

    // 알림 일괄 삭제 -> 다시 확인**************
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
                alertEnt.setAlmessage("문의답변을확인하세요!");
                alRepository3.save(alertEnt);
                //System.out.println(alertEnt.getAlmessage());
            }
            // 후기는 다시 확인
            else if(alertEnt.getAltype() == 2L) {
                alertEnt.setAlmessage("후기를확인하세요!");
                alRepository3.save(alertEnt);
            }
            else if(alertEnt.getAltype() == 3L) {
                alertEnt.setAlmessage("댓글을확인하세요!");
                alRepository3.save(alertEnt);
            }
            else if(alertEnt.getAltype() == 4L) {
                alertEnt.setAlmessage("대댓글을확인하세요!");
                alRepository3.save(alertEnt);
            }
            else if(alertEnt.getAltype() == 5L) {
                alertEnt.setAlmessage("등급이한단계올랐어요!");
                alRepository3.save(alertEnt);
            }
            else if(alertEnt.getAltype() == 6L) {
                alertEnt.setAlmessage("후기를작성하세요!");
                alRepository3.save(alertEnt);
            }
            else if(alertEnt.getAltype() == 7L) {
                alertEnt.setAlmessage("새글을확인하세요!");
                alRepository3.save(alertEnt);
            }
            return 0;
        } catch (Exception e) {
            e.getStackTrace();
            System.out.println("알림저장서비스impl에러======="+e);
            return -1;
        }
    }

    // 알림 전체 목록 조회 불러오기(페이지) (검색X)
    // 읽기 여부 상관X
    @Override
    public List<AlertEntity> selectAlertAllList(Pageable page, String uid) {
        try {
            return alRepository3.findByMember_uid(page, uid);
        } catch (Exception e) {
            return null;
        }
    }

    // 읽지 않은(1) 알림 목록 조회
    @Override
    public List<AlertEntity> selectUnReadAlertList(Pageable page, String uid, Long alread) {
        try {
            return alRepository3.findByMember_uidAndAlread(page, uid, alread);
        } catch (Exception e) {
            return null;
        }
    }

    // 알림 1개 조회(상세) 후 1->0으로 수정하기
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
    public AlertEntity alertTypeChk(Long alno, Long altype) {
        try {
            return alRepository3.findById(alno).orElse(null);
        } catch (Exception e) {
            e.getStackTrace();
            return null;
        }
    }

    // 읽지 않은(1) 알림 개수 호출
    @Override
    public Long alertUnReadCount(Long alread, String uid) {
        try {
            return alRepository3.countByAlreadAndMember_uid(alread, uid);
        } catch (Exception e) {
            e.getStackTrace();
            return null;
        }
    }
    
    // 알림 1(읽지않음) -> 0(읽음) 수정
    @Override
    public int updateAlread(AlertEntity alertEntity) {
        try {
            System.out.println("읽은여부수정여기=====");
            //System.out.println(alertEntity);
            
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
        System.out.println("문의답변알림서비스===");
        //System.out.println(inquireEnt); // 답변 적은 해당 문의글 나옴
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
                // alertService3.updateAlread(alertEnt);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("알람서비스에러====="+e);
                sseEmitters.remove(userid);
            }
        }
    }

    // 후기 작성 완료 알림 (판매자에게 알림)
    @Override
    public void sendReviewAlert(ChatroomEntity chatRoonEnt, AlertEntity alertEnt) {
        System.out.println("후기알림서비스===========");
        //System.out.println(chatRoonEnt.getBoard().getMember().getUid());
        String userid = chatRoonEnt.getBoard().getMember().getUid();
        if(sseEmitters.containsKey(userid)) {
            SseEmitter sseEmitter = sseEmitters.get(userid);
            try {
                sseEmitter.send(SseEmitter.event().name("sendAnswerAlert").data(userid + "님이 작성하신 글에 후기를 확인하세요"));
                // 알림 alread (1->0) 읽음으로 바꾸기 호출
                // alertService3.updateAlread(alertEnt);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("알람서비스에러====="+e);
                sseEmitters.remove(userid);
            }
        }
    }

    // 댓글 알림
    @Override
    public void sendCommAlert(BoardEntity boardEnt, AlertEntity alertEnt) {
        System.out.println("댓글알림서비스===");
        //System.out.println(boardEnt);
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
                // alertService3.updateAlread(alertEnt);
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
        System.out.println("대댓글알림서비스=====");
        //System.out.println(commEnt);
        Long comm = commEnt.getCono();
        //System.out.println(comm);
        CommEntity cEntity = commRepository2.getById(comm);
        //System.out.println(cEntity.getMember().getUid());
        String userid = cEntity.getMember().getUid();
        if(sseEmitters.containsKey(userid)) {
            SseEmitter sseEmitter = sseEmitters.get(userid);
            try {
                sseEmitter.send(SseEmitter.event().name("sendRecommentAlert").data(userid + "님이 쓰신 댓글에 대댓글이 추가되었어요!"));
                // 알림 alread (1->0) 읽음으로 바꾸기 호출
                // alertService3.updateAlread(alertEnt);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("알람서비스에러====="+e);
                sseEmitters.remove(userid);
            }
        }
    }

    // 등급 업 알림 (판매자, 구매자 둘 다)
    @Override
    public void sendRankUpAlert(ChatViewEntity chatViewEnt, AlertEntity alertEnt) {
        System.out.println("등급업알림서비스====");
        //System.out.println(chatViewEnt);
        //ChatViewEntity(crno=3, startMessage=1, writer=gg, clickperson=uu, boardBno=35, crreport=N, crregdate=2022-05-18 11:50:14.511, 
        //reviewRevno=null, chstate=TDONE)
        String wrUid = chatViewEnt.getWriter();
        if(sseEmitters.containsKey(wrUid)) {
            SseEmitter sseEmitter = sseEmitters.get(wrUid);
            try {
                sseEmitter.send(SseEmitter.event().name("sendRankUpAlert").data(wrUid + "님 등급이 한 단계 올랐어요!"));
                // 알림 alread (1->0) 읽음으로 바꾸기 호출
                // alertService3.updateAlread(alertEnt);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("알람서비스에러====="+e);
                sseEmitters.remove(wrUid);
            }
        }
        String clUid = chatViewEnt.getClickperson();
        if(sseEmitters.containsKey(clUid)) {
            SseEmitter sseEmitter = sseEmitters.get(clUid);
            try {
                sseEmitter.send(SseEmitter.event().name("sendRankUpAlert").data(clUid + "님 등급이 한 단계 올랐어요!"));
                // 알림 alread (1->0) 읽음으로 바꾸기 호출
                // alertService3.updateAlread(alertEnt);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("알람서비스에러====="+e);
                sseEmitters.remove(clUid);
            }
        }
    }

    // 후기 작성 여부 알림 (구매자에게 알림)
    @Override
    public void sendInsertReviewAlert(ChatViewEntity chatViewEnt, AlertEntity alertEnt) {
        System.out.println("후기작성여부알림서비스========");
        String clickUid = chatViewEnt.getClickperson();
        if(sseEmitters.containsKey(clickUid)) {
            SseEmitter sseEmitter = sseEmitters.get(clickUid);
            try {
                sseEmitter.send(SseEmitter.event().name("sendInsertReviewAlert").data(clickUid + "님 구매하신 능력이 마음에 드셨다면 후기 부탁드려요!"));
                // 알림 alread (1->0) 읽음으로 바꾸기 호출
                // alertService3.updateAlread(alertEnt);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("알람서비스에러====="+e);
                sseEmitters.remove(clickUid);
            }
        }
    }

    // 체크한 관심사 새 글 알림
    @Override
    public void sendInterestAlert(BoardInterest bodInEnt, AlertEntity alertEnt) {
        System.out.println("관심사알림서비스========");
    }


}
