package com.example.service;

import java.util.List;

import com.example.entity.BoardImageEntity;

import org.springframework.stereotype.Service;

@Service
public interface BoardImageService1 {

    // 이미지 등록하기
    public int insertBoardImage(List<BoardImageEntity> list);

    // 이미지 가져오기(1개)
    public BoardImageEntity selectBoardImageOne(long bino);

    // 이미지 수정
    public int updateBoardImage(BoardImageEntity bimage);

    // 이미지 삭제
    public int deleteBoardImage(long bino);

    // 이미지 가져오기(여러개)
    public List<Long> selectBoardImage(long bino);
}
