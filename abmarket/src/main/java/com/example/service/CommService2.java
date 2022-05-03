package com.example.service;

import java.util.List;

import com.example.entity.CommEntity;
import com.example.entity.RecommentEntity;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface CommService2 {

    // 댓글 쓰기
    public int insertComm(CommEntity comm);

    // 댓글 목록
    public List<CommEntity> selectListComm(Pageable page, Long bno);

    // 댓글 삭제
    public int deleteComm(String uid, Long cono);

    // 댓글 개수 구하기
    public Long countComm(long bno);

    // 댓글 수정
    public int updateComm(String uid, CommEntity comm);

    // 자기가 쓴 글 표시하기
    public int checkMine(Long cono, String uid);

    // 대댓글 쓰기
    public int insertRecomm(RecommentEntity recomment);

    // 대댓글 삭제
    public int deleteRecomm(String uid, Long reno);

}
