package com.example.service;

import java.util.List;

import com.example.entity.BoardEntity;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface BoardService1 {

    // 게시물 등록
    public int insertBoard(BoardEntity board);

    // 게시물 목록
    public List<BoardEntity> selectList(Pageable page);

    // 게시물 전체개수 (페이지네이션)
    public long selectBoardCount();

    // 게시물 조회
    public BoardEntity selectOne(long bno);

    // 게시물 수정
    public int updateBoardOne(BoardEntity board);

    // 게시물 삭제
    public int deleteBoardOne(long bno);

}
