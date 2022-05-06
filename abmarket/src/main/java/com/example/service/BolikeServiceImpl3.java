package com.example.service;

import java.util.List;

import javax.transaction.Transactional;

import com.example.entity.BolikeEntity;
import com.example.repository.BolikeRepository3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BolikeServiceImpl3 implements BolikeService3 {
    
    @Autowired
    BolikeRepository3 bolikeRepository3;

    @Autowired
    BolikeService3 bolikeService3;

    // 찜 등록(데이터X) -> 데이터 생성
    // 찜 취소(데이터O) -> 데이터 삭제

    // 찜 취소 (삭제)
    @Override
    @Transactional //에러 -> cannot reliably process 'remove' call => 설정함
    public int deleteBolike(String uid, Long bno) {
        try {
            bolikeRepository3.deleteByMemberUidAndBoardBno(uid, bno);
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
    public int chkBolike(String uid, Long bno) {
        try {
            BolikeEntity bEntity = bolikeRepository3.findByMemberUidAndBoardBno(uid, bno);
            //System.out.println("service===" + bEntity);
            if(bEntity != null) {
                return 1; //db에 있음
            }
            return 0; //db에 없음
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    // 해당 게시물 찜 총 갯수
    @Override
    public Long countBolike(Long bno) {
        try {
            return bolikeRepository3.countByBoard_bno(bno);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    // 찜 전체 목록 조회 (회원 본인이 찜한 목록)
    @Override
    public List<BolikeEntity> selectlistBolike(Pageable page, String uid) {
        try {
            return bolikeRepository3.findByMemberUid(page, uid);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 찜 1개 조회 (확인용)
    @Override
    public BolikeEntity bolikeOne(Long bolno) {
        try {
            return bolikeRepository3.findById(bolno).orElse(null);
        } catch (Exception e) {
            return null;
        }
    }


}
