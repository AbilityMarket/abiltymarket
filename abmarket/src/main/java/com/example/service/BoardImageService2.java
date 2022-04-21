package com.example.service;

import java.util.List;

import com.example.entity.BoardImageEntity;

import org.springframework.stereotype.Service;

@Service
public interface BoardImageService2 {

    // 이미지 일괄추가
    public int insertImage(List<BoardImageEntity> imageList);

    // 이미지 1개 조회
    public BoardImageEntity selectBoardImageOne(long bino);
    // or 일괄추가로 1개를 해결할 수 있을지

    // 이미지 1개 삭제
    public int deleteBoardImageOne(long bino);

}
