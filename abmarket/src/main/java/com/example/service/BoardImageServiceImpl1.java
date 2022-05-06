package com.example.service;

import java.util.List;

import com.example.entity.BoardImageEntity;
import com.example.entity.BoardImageEntityProjection;
import com.example.repository.BoardImageRepository1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardImageServiceImpl1 implements BoardImageService1 {

    @Autowired
    BoardImageRepository1 bImageRepository1;

    // 서브 이미지 등록
    @Override
    public int insertBoardImage(List<BoardImageEntity> list) {
        try {
            bImageRepository1.saveAll(list);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 서브 이미지 가져오기
    @Override
    public BoardImageEntity selectBoardImage(long bino) {
        try {
            return bImageRepository1.findById(bino).orElse(null);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 이미지 호출
    @Override
    public List<BoardImageEntityProjection> selectBoardImageProjection(long bno) {
        return bImageRepository1.findByBoard_bno(bno);
    }

    // 서브 이미지 수정
    @Override
    public int updateBoardImage(List<BoardImageEntity> list) {
        try {
            bImageRepository1.saveAll(list);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 서브 이미지 일괄삭제
    @Override
    public int deleteBoardImageBatch(long bino) {
        try {
            bImageRepository1.deleteById(bino);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 서브 이미지 부분삭제
    @Override
    public int deleteBoardImage(long[] bino) {
        try {
            for (long bno : bino) {
                bImageRepository1.deleteById(bno);
            }
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
