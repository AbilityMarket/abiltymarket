package com.example.service;

import java.util.List;

import com.example.entity.ReviewImageEntity;

import org.springframework.stereotype.Service;

@Service
public interface ReviewImageService1 {

    // 후기 이미지 등록
    public long insertReviewImage(List<ReviewImageEntity> list);

    // 후기 이미지 가져오기(1개)
    public ReviewImageEntity selectReviewImageOne(long rvimno);

    // 후기 이미지 수정
    public int updateReviewImage(ReviewImageEntity ReviewImage);

    // 후기 이미지 삭제
    public int deleteReviewImage(long rvimno);

    // 후기 이미지 가져오기(여러개)
    public List<Long> selectReviewImage(long rvimno);
}
