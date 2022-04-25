package com.example.service;

import java.util.List;

import com.example.entity.ReviewImageEntity;
import com.example.repository.ReviewImageRepository1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewImageServiceImpl1 implements ReviewImageService1 {

    @Autowired
    ReviewImageRepository1 rImgRepository1;

    // 후기 이미지 등록
    @Override
    public long insertReviewImage(List<ReviewImageEntity> list) {
        try {

        } catch (Exception e) {
        }
        return 0;
    }

    // 후기 이미지 조회(1개)
    @Override
    public ReviewImageEntity selectReviewImageOne(long rvimno) {
        try {
            return rImgRepository1.findById(rvimno).orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 후기 이미지 수정
    @Override
    public int updateReviewImage(ReviewImageEntity ReviewImage) {
        try {
            rImgRepository1.save(ReviewImage);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 후기 이미지 삭제
    @Override
    public int deleteReviewImage(long rvimno) {
        try {
            rImgRepository1.deleteById(rvimno);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 후기 이미지 조회(여러개)
    @Override
    public List<Long> selectReviewImage(long rvimno) {
        try {

        } catch (Exception e) {
        }
        return null;
    }

}
