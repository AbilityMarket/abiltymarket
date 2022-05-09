package com.example.service;

import java.util.List;

import com.example.entity.ReviewImageEntity;
import com.example.entity.ReviewImageEntityProjection;

import org.springframework.stereotype.Service;

@Service
public interface ReviewImageService1 {

    // 후기 이미지 등록
    public int insertReviewImage(ReviewImageEntity reviewImage);

    // 후기 이미지 가져오기(1개)
    public ReviewImageEntity selectReviewImageOne(long rvimno);

    // 후기 이미지 수정
    public int updateReviewImage(ReviewImageEntity reviewImage);

    // 후기 이미지 삭제
    public int deleteReviewImage(long rvimno);

    // 후기 이미지 등록(여러개)
    public int insertReviewImageBatch(List<ReviewImageEntity> list);

    // 후기 이미지 가져오기(여러개)
    public ReviewImageEntity selectReviewImage(long rvimno);

    // 이미지 호출하기
    public List<ReviewImageEntityProjection> selectReviewImageProjection(long revno);

    // 후기 이미지 수정하기(일괄)
    public int updateReviewImageBatch(List<ReviewImageEntity> list);

    // 후기 이미지 삭제하기(부분)
    public int deleteReviewImage(long[] rvimno);
}
