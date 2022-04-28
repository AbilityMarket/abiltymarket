package com.example.service;

import java.util.List;

import com.example.entity.BoardEntity;
import com.example.repository.BoardRepository1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BoardServiceImpl1 implements BoardService1 {

    @Autowired
    BoardRepository1 bRepository1;

    // 게시글 작성
    @Override
    public int insertBoard(BoardEntity board) {
        try {
            bRepository1.save(board);
            return 1;

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 게시글 목록(페이지+검색)
    @Override
    public List<BoardEntity> selectListBoard(Pageable page, String btitle) {
        try {
            return bRepository1.findByBtitleContainingOrderByBnoDesc(page, btitle);

        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }
    }

    // 게시글 갯수
    @Override
    public long selectBoardCount(String text) {
        return 0;
    }

    // 게시글 상세
    @Override
    public BoardEntity selectBoardOne(long bno) {
        try {
            return bRepository1.findById(bno).orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 게시글 수정
    @Override
    public int updateBoardOne(BoardEntity board) {
        try {
            bRepository1.save(board);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 게시글 삭제
    @Override
    public int deleteBoardOne(long bno) {
        try {
            bRepository1.deleteById(bno);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

}
