package com.example.service;

import com.example.entity.ReviewEntity;
import com.example.repository.ReviewRepository1;

import org.springframework.beans.factory.annotation.Autowired;
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

}
