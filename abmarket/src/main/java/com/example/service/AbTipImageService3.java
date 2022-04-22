package com.example.service;

import java.util.List;

import com.example.entity.AbTipImageEntity;

import org.springframework.stereotype.Service;

@Service
public interface AbTipImageService3 {
    
    // 이미지 등록
    public int insertAbTipImage(List<AbTipImageEntity> list);

    // 이미지 수정
    public int updateAbTipImage(AbTipImageEntity abtipimg);

    // 이미지 삭제
    public int deleteAbTipImage(long abino);

    // 이미지 가져오기
    public AbTipImageEntity selectAbTipImage(long abino);

}
