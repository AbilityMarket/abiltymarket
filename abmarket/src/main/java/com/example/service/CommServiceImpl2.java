package com.example.service;

import java.util.List;

import com.example.entity.CommEntity;
import com.example.entity.RecommentEntity;
import com.example.repository.CommRepository2;
import com.example.repository.RecommentRepository2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CommServiceImpl2 implements CommService2 {

    @Autowired
    CommRepository2 cRepository2;

    @Autowired
    RecommentRepository2 reRepository;

    // 댓글쓰기
    @Override
    public int insertComm(CommEntity comm) {
        try {
            cRepository2.save(comm);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    // 댓글 목록 가져오기
    @Override
    public List<CommEntity> selectListComm(Pageable page, Long bno) {
        try {
            return cRepository2.findByBoard_bnoOrderByCoregdateDesc(page, bno);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    // 댓글 삭제하기
    @Override
    public int deleteComm(String uid, Long cono) {
        try {
            // 댓글을 작성한 사람만 삭제가능
            CommEntity comm = cRepository2.findById(cono).orElse(null);
            if (comm.getMember().getUid().equals(uid)) {
                cRepository2.deleteById(cono);
                return 1;
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    // 댓글 개수 구하기
    @Override
    public Long countComm(long bno) {
        try {
            long count1 = cRepository2.countByBoard_bno(bno);
            List<CommEntity> list = cRepository2.findByBoard_bno(bno);
            Long count2 = 0L;
            for (CommEntity comm : list) {
                count2 += reRepository.countByComm_cono(comm.getCono());
            }
            return count1 + count2;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 댓글 수정하기
    @Override
    public int updateComm(String uid, CommEntity comm) {
        try {

            CommEntity comm2 = cRepository2.findById(comm.getCono()).orElse(null);
            // 본인 아이디랑 같으면
            if (comm2.getMember().getUid().equals(uid)) {
                comm2.setCocontent(comm.getCocontent());
                comm2.setCoopen(comm.getCoopen());
                cRepository2.save(comm2);
                System.out.println(comm2);
                return 1;
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }

    }

    // 대댓글 달기
    @Override
    public int insertRecomm(RecommentEntity recomment) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // 자기가 쓴 글 표시하기
    @Override
    public int checkMine() {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
