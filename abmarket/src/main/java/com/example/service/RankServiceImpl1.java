package com.example.service;

import java.util.List;

import com.example.entity.RankEntity;
import com.example.repository.RankRepository1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RankServiceImpl1 implements RankService1 {

    @Autowired
    RankRepository1 raRepository1;

    // 등급내용 작성하기
    @Override
    public int insertRank(RankEntity rank) {
        // TODO Auto-generated method stub
        return 0;
    }

    // 등급내용 수정하기
    @Override
    public int updateRank(RankEntity rank) {
        // TODO Auto-generated method stub
        return 0;
    }

    // 등급내용 삭제하기
    @Override
    public int deleteRank(String name) {
        // TODO Auto-generated method stub
        return 0;
    }

    // 이미지 등록
    @Override
    public long insertRankImage(List<RankEntity> list) {
        // TODO Auto-generated method stub
        return 0;
    }

    // 이미지 가져오기(1개)
    @Override
    public RankEntity selectRankImage(String rname) {
        // TODO Auto-generated method stub
        return null;
    }

    // 이미지 수정
    @Override
    public int updateRankImage(RankEntity rname) {
        // TODO Auto-generated method stub
        return 0;
    }

    // 이미지 삭제
    @Override
    public int deleteRankImage(String rname) {
        // TODO Auto-generated method stub
        return 0;
    }

}
