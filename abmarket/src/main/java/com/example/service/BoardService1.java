package com.example.service;

import java.util.List;
import java.util.Map;

import com.example.entity.BoardEntity;

import org.springframework.stereotype.Service;

@Service
public interface BoardService1 {

    // 게시글 작성
    public int insertBoard(BoardEntity board);

    // 게시글 조회(검색어+페이지네이션)
    public List<BoardEntity> selectListBoard(Map<String, Object> map);

    // 게시글 개수(페이지네이션)
    public long selectBoardCount(Map<String, Object> map);

    // 게시글 상세
    public BoardEntity selectBoardOne(long bno);

    // 게시글 수정
    public int updateBoardOne(BoardEntity board);

    // 게시글 삭제
    public int deleteBoardOne(long bno);

}
