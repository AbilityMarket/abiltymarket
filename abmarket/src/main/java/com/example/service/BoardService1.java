package com.example.service;

import java.util.List;

import com.example.entity.BoardEntity;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface BoardService1 {

    // 게시글 작성
    public int insertBoard(BoardEntity board);

    // 게시글 목록(검색+페이지)
    public List<BoardEntity> selectListBoard(Pageable page, String btitle);

    // 게시글 개수(페이지네이션용, 검색어)
    public long selectBoardCount(String text);

    // 게시글 상세
    public BoardEntity selectBoardOne(long bno);

    // 게시글 수정
    public int updateBoardOne(BoardEntity board);

    // 게시글 삭제
    public int deleteBoardOne(long bno);

}
