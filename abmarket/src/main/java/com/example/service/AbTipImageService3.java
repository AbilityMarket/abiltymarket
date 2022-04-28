package com.example.service;


import java.util.List;

import com.example.entity.AbTipImageEntity;

import org.springframework.stereotype.Service;


// 능력 팁 이미지 서비스
@Service
public interface AbTipImageService3 {
    
    // 이미지 등록
    public int insertAbTipImage(AbTipImageEntity abtimg);

    // 이미지 수정
    public int updateAbTipImage(AbTipImageEntity abtipimg);

    // 이미지 삭제
    public int deleteAbTipImage(String userid, long abino);

    // 팁 게시판 조회 시 해당 이미지 가져가기
    public List<AbTipImageEntity> selectAbTipImage(long abino);

}
