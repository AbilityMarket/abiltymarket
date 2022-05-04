package com.example.service;

import java.util.List;

import com.example.entity.BolikeEntity;
import com.example.repository.BolikeRepository3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BolikeServiceImpl3 implements BolikeService3 {
    
    @Autowired
    BolikeRepository3 bolikeRepository3;

    // 찜 등록(데이터X) -> 데이터 생성
    // 찜 취소(데이터O) -> 데이터 삭제

    // 찜 취소 (삭제)
    @Override
    public int deleteBolike(Long bolno) {
        try {
            bolikeRepository3.deleteById(bolno);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 찜 하기 (등록)
    @Override
    public int insertBolike(BolikeEntity bolike) {
        try {
            bolikeRepository3.save(bolike);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 찜 유무 확인
    @Override
    public Long chkBolike(String uid, Long bno) {
        try {
            return bolikeRepository3.findByMember_uidAndBoard_bno(uid, bno);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 찜 총 갯수
    @Override
    public Long countBolike(Long bno) {
        try {
            return bolikeRepository3.countByBoard_bno(bno);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    // 찜 전체 목록
    @Override
    public List<BolikeEntity> selectlistBolike(Pageable page, String uid, Long bno) {
        try {
            return bolikeRepository3.findByMemberAndBoard(page, uid, bno);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
