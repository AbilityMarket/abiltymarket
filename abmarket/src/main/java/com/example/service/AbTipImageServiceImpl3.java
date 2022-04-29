package com.example.service;


import java.util.List;

import com.example.entity.AbTipImageEntity;
import com.example.repository.AbTipImageRepository3;
import com.example.repository.AbTipRepository3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AbTipImageServiceImpl3 implements AbTipImageService3 {

    @Autowired AbTipImageRepository3 abtiRepository3;

    @Autowired AbTipRepository3 abtRepository3;

    // 이미지 삭제
    @Override
    public int deleteAbTipImage(String userid, long abino) {
        try {
            abtiRepository3.deleteById(abino);
            return 1;
        } catch (Exception e) {
            e.getStackTrace();
            return 0;
        }
    }

    // 이미지 등록
    @Override
    public int insertAbTipImage(AbTipImageEntity abtimg) {
        try {
            abtiRepository3.save(abtimg);
            return 1;
        } catch (Exception e) {
            e.getStackTrace();
            return 0;
        }
    }

    // 팁 게시판 조회 시 해당 이미지 가져가기
    @Override
    public List<AbTipImageEntity> selectAbTipImage(long abino) {
        try {
            List<AbTipImageEntity> list = abtiRepository3.findByAbtip_abtno(abino);
            return list;
        } catch (Exception e) {
            e.getStackTrace();
            return null;
        }
    }

    // 이미지 수정
    @Override
    public int updateAbTipImage(AbTipImageEntity abtipimg) {
        try {
            abtiRepository3.save(abtipimg);
            return 1;
        } catch (Exception e) {
            e.getStackTrace();
            return 0;
        }
    }

    // 기존 이미지 불러오기(수정 시)
    @Override
    public AbTipImageEntity selectOneAbTipImage(long abino) {
        try {
            return abtiRepository3.findById(abino).orElse(null);
        } catch (Exception e) {
            e.getStackTrace();
            return null;
        }
    }




    
}
