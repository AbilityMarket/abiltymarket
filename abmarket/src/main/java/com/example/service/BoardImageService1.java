package com.example.service;

import java.util.List;

import com.example.entity.BoardImageEntity;
import com.example.entity.BoardImageEntityProjection;

import org.springframework.stereotype.Service;

@Service
public interface BoardImageService1 {

    // 서브 이미지 등록하기
    public int insertBoardImage(List<BoardImageEntity> list);

    // 게시물 이미지에 해당하는 서브 이미지 가져오기
    public BoardImageEntity selectBoardImage(long bino);

    // 게시물 번호에 해당하는 n개의 서브 이미지 번호를 가져옴
    public List<BoardImageEntityProjection> selectBoardImageProjection(long bno);

    // 이미지 일괄수정
    public int updateBoardImage(List<BoardImageEntity> list);

    // 이미지 일괄삭제
    public int deleteBoardImage(long[] bino);
}
