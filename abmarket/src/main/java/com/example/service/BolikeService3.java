package com.example.service;

import java.util.List;

import com.example.entity.BolikeEntity;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface BolikeService3 {

    // 찜 하기 (등록)
    // 등록시 게시판 글번호와 해당 작성자 필요
    public int insertBolike(BolikeEntity bolike, String uid, Long bno);

    // 찜 취소 (삭제)
    public int deleteBolike(String uid, Long bno);

    // 찜 전체 목록 조회 (회원 본인이 찜한 목록)
    public List<BolikeEntity> selectlistBolike(Pageable page, String uid);
    //public List<BolikeEntity> selectlistBolike(Pageable page, String uid, Long bno);

    // 해당 게시물 찜 총 갯수
    // 해당 글번호만 필요
    public Long countBolike(Long bno);

    // 찜 유무 확인
    // db에 bno, uid 체크
    public int chkBolike(String uid, Long bno);

    // 찜 1개 조회 (작업 중 확인용으로 만듦)
    public BolikeEntity bolikeOne(Long bolno);

}
