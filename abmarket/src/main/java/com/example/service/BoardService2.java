package com.example.service;

import java.util.List;
import java.util.Map;

import com.example.entity.BoardEntity;

import org.springframework.stereotype.Service;

@Service
public interface BoardService2 {

    // 게시글 작성
    public int insertBoard(BoardEntity board);

    // 게시글 수정
    public BoardEntity updateBoard(BoardEntity board);

    // 게시글 삭제
    public int deleteBoard(BoardEntity board);

    // 게시글 목록
    public List<BoardEntity> selectListBoard();

    // 게시글 상세(1개)
    public BoardEntity selectOneBoard(Long bno);

    // 게시글 목록(검색어 + 페이지네이션)
    public List<BoardEntity> selectListBoardSearchAndPagenation(Map<String, Object> map);

    // 게시글 전체 개수
    public long selectBoardCount(Map<String, Object> map);

}
