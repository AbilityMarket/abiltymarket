package com.example.service;

import java.util.List;

import com.example.entity.BoardImageEntity;
import com.example.repository.BoardImageRepository1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardImageServiceImpl1 implements BoardImageService1 {

    @Autowired
    BoardImageRepository1 bImageRepository1;

    // 이미지 등록하기
    @Override
    public long insertBoardImage(List<BoardImageEntity> list) {
        // TODO Auto-generated method stub
        return 0;
    }

    // 이미지 가져오기(1개)
    @Override
    public BoardImageEntity selectBoardImageOne(long bino) {
        try {
            return bImageRepository1.findById(bino).orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 이미지 수정
    @Override
    public int updateBoardImage(BoardImageEntity bimage) {
        try {
            bImageRepository1.save(bimage);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 이미지 삭제
    @Override
    public int deleteBoardImage(long bino) {
        try {
            bImageRepository1.deleteById(bino);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 이미지 가져오기(여러개)
    @Override
    public List<Long> selectBoardImage(long bino) {
        // TODO Auto-generated method stub
        return null;
    }

}
