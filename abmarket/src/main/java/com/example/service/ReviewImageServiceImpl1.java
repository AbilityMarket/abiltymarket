package com.example.service;

import java.util.List;

import com.example.entity.ReviewImageEntity;
import com.example.entity.ReviewImageEntityProjection;
import com.example.repository.ReviewImageRepository1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewImageServiceImpl1 implements ReviewImageService1 {

    @Autowired
    ReviewImageRepository1 rImgRepository1;

    // 후기 이미지 등록
    @Override
    public int insertReviewImage(ReviewImageEntity reviewImage) {
        try {
            rImgRepository1.save(reviewImage);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
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
    public int updateReviewImage(ReviewImageEntity reviewImage) {
        try {
            rImgRepository1.save(reviewImage);
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

    // 후기 이미지 등록(여러개)
    @Override
    public int insertReviewImageBatch(List<ReviewImageEntity> list) {
        try {
            rImgRepository1.saveAll(list);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 후기 이미지 가져오기(여러개)
    @Override
    public ReviewImageEntity selectReviewImage(long rvino) {
        try {
            return rImgRepository1.findById(rvino).orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 이미지 호출하기
    @Override
    public List<ReviewImageEntityProjection> selectReviewImageProjection(long revno) {
        return rImgRepository1.findByReview_revno(revno);
    }

    // 후기 이미지 수정하기(일괄)
    @Override
    public int updateReviewImageBatch(List<ReviewImageEntity> list) {
        try {
            rImgRepository1.saveAll(list);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 후기 이미지 삭제하기(부분)
    @Override
    public int deleteReviewImage(long[] rvimno) {
        try {
            for (long revno : rvimno) {
                rImgRepository1.deleteById(revno);
            }
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

}
