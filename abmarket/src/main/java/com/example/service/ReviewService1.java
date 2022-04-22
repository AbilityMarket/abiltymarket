package com.example.service;

import com.example.entity.ReviewEntity;

import org.springframework.stereotype.Service;

@Service
public interface ReviewService1 {

    // 후기 작성하기(평점포함)
    public int insertReview(ReviewEntity review);

    // 후기 조회하기
    public ReviewEntity selectReview(long revno);

    // 후기 수정하기
    public int updateReview(ReviewEntity review);

    // 후기 삭제하기
    public int deleteReview(long revno);

}
