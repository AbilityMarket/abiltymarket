package com.example.service;

import java.util.List;

import com.example.entity.BolikeEntity;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface BolikeService3 {

    // 찜 하기 (등록)
    // 등록시 게시판 글번호와 해당 작성자 필요
    public int insertBolike(BolikeEntity bolike);

    // 찜 취소 (삭제)
    public int deleteBolike(String uid, Long bno);

    // 찜 목록
    public List<BolikeEntity> selectlistBolike(Pageable page, String uid, Long bno);

    // 찜 총 갯수
    // 해당 글번호만 필요
    public Long countBolike(Long bno);

    // 찜 유무 확인
    // db에 bno, uid 체크
    public int chkBolike(String uid, Long bno);

}
