package com.example.service;

import com.example.entity.CommEntity;

import org.springframework.stereotype.Service;

@Service
public interface CommService2 {

    // 댓글 쓰기
    public int insertComm(CommEntity comm);

    // 게시글 목록(검색+페이지)
    // public List<BoardEntity> selectListBoard(Pageable page, String btitle);

    // // 게시글 개수(페이지네이션용, 검색어)
    // public long selectBoardCount(String text);

    // // 게시글 상세
    // public BoardEntity selectBoardOne(long bno);

    // // 게시글 수정
    // public int updateBoardOne(BoardEntity board);

    // // 게시글 삭제
    // public int deleteBoardOne(long bno);
}
