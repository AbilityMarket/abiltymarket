package com.example.service;

import java.util.List;

import com.example.entity.AbTipImageEntity;
import com.example.repository.AbTipImageRepository3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AbTipImageServiceImpl3 implements AbTipImageService3 {

    @Autowired AbTipImageRepository3 abtiRepository3;

    // 이미지 삭제
    @Override
    public int deleteAbTipImage(long abino) {
        try {
            abtiRepository3.deleteById(abino);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    // 이미지 등록
    @Override
    public int insertAbTipImage(List<AbTipImageEntity> list) {
        try {
            abtiRepository3.saveAll(list);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    // 이미지 가져오기
    @Override
    public AbTipImageEntity selectAbTipImage(long abino) {
        return null;
    }

    // 이미지 수정
    @Override
    public int updateAbTipImage(AbTipImageEntity abtipimg) {
        return 0;
    }

    // 팁코드(abtno)와 일치하는 팁이미지코드(abino) 가져오기
    @Override
    public List<Long> selectAbtipImgList(long abtno) {
        // try {
        //     return abtiRepository3.findById(abtno).orElse(null);
        // } catch (Exception e) {
        //     return null;
        // }
        return null;
    }
    
}
