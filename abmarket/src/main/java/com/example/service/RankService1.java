package com.example.service;

import com.example.entity.RankEntity;

import org.springframework.stereotype.Service;

@Service
public interface RankService1 {

    // 등급내용 작성하기
    public int insertRank(RankEntity rank);

    // 등급내용 조회하기
    public RankEntity selectRank(String rname);

    // 등급내용 수정하기
    public int updateRank(RankEntity rank);

    // 등급내용 삭제하기
    public int deleteRank(String rname);

    // 이미지 등록
    public long insertRankImage(RankEntity rankimage);

    // 이미지 가져오기(1개)
    public RankEntity selectRankImage(String rimagename);

    // 이미지 수정
    public int updateRankImage(RankEntity rankimage);

    // 이미지 삭제
    public int deleteRankImage(String rimagename);

}
