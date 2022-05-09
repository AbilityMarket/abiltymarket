package com.example.service;

import java.util.List;

import com.example.entity.ReviewEntity;
import com.example.repository.ReviewRepository1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl1 implements ReviewService1 {

    @Autowired
    ReviewRepository1 revRepository1;

    // 후기 작성하기(평점포함)
    @Override
    public int insertReview(ReviewEntity review) {
        try {
            revRepository1.save(review);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 후기 조회하기
    @Override
    public ReviewEntity selectReview(long revno) {
        try {
            return revRepository1.findById(revno).orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 후기 수정하기
    @Override
    public int updateReview(ReviewEntity review) {
        try {
            revRepository1.save(review);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 후기 삭제하기
    @Override
    public int deleteReview(long revno) {
        try {
            revRepository1.deleteById(revno);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 후기 목록 조회 (페이지, 내용검색)
    @Override
    public List<ReviewEntity> selectListReview(Pageable page, String revcontent) {
        try {
            return revRepository1.findByRevcontentContainingOrderByRevnoDesc(page, revcontent);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 후기 전체 갯수
    // @Override
    // public Long countReview(Long bno) {
    // try {
    // return revRepository1.countByBoard_bno(bno);
    // } catch (Exception e) {
    // e.printStackTrace();
    // return null;
    // }
    // }
}
