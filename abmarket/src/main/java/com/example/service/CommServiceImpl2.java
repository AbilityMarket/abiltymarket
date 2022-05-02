package com.example.service;

import java.util.List;

import com.example.entity.CommEntity;
import com.example.entity.RecommentEntity;
import com.example.repository.CommRepository2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CommServiceImpl2 implements CommService2 {

    @Autowired
    CommRepository2 cRepository2;

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
    public List<CommEntity> selectListBoard(Pageable page) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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

    @Override
    public Long countComm(long bno) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int updateComm(CommEntity comm) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int insertRecomm(RecommentEntity recomment) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int checkMine() {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}