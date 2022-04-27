package com.example.service;

import java.util.List;

import com.example.entity.AbTipImageEntity;

import org.springframework.stereotype.Service;


// 능력 팁 이미지 서비스
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

    // 팁코드(abtno)와 일치하는 팁이미지코드(abino) 가져오기 (수정)
    public List<Long> selectAbtipImgList(long abtno);

}
