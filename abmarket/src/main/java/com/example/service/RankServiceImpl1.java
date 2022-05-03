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
        try {
            raRepository1.save(rank);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 등급내용 조회하기
    @Override
    public RankEntity selectRank(String rname) {
        try {
            return raRepository1.findById(rname).orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 등급내용 수정하기
    @Override
    public int updateRank(RankEntity rank) {
        try {
            raRepository1.save(rank);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 등급내용 삭제하기
    @Override
    public int deleteRank(String rname) {
        try {
            raRepository1.deleteById(rname);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 이미지 등록
    @Override
    public long insertRankImage(RankEntity rankimage) {
        try {
            raRepository1.save(rankimage);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 이미지 가져오기(1개)
    @Override
    public RankEntity selectRankImage(String rname) {
        try {
            return raRepository1.findById(rname).orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 이미지 수정
    @Override
    public int updateRankImage(RankEntity rankimage) {
        try {
            raRepository1.save(rankimage);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 이미지 삭제
    @Override
    public int deleteRankImage(String rname) {
        try {
            raRepository1.deleteById(rname);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

}
