package com.example.service;


import java.util.List;

import com.example.entity.AbTipImageEntity;
import com.example.entity.AbTipImageEntityProjection;

import org.springframework.stereotype.Service;


// 능력 팁 이미지 서비스
@Service
public interface AbTipImageService3 {
    
    // 이미지 등록
    //public int insertAbTipImage(AbTipImageEntity abtimg);

    public int insertAbTipImage(List<AbTipImageEntity> list);

    // 이미지 1개 수정
    public int updateAbTipImage(AbTipImageEntity abtipimg);

    // 이미지 일괄 수정
    public int updateAbTipImgList(List<AbTipImageEntity> list);

    // 기존 이미지 불러오기(수정 시)
    public AbTipImageEntity selectOneAbTipImage(long abino);

    // 이미지 삭제
    public int deleteAbTipImage(String userid, long abino);

    // 이미지 부분 삭제
    public int deleteAbTipImgPart(Long[] abino);

    // 팁 게시판 조회 시 해당 이미지 가져가기 (url버전)
    public List<AbTipImageEntityProjection> selectAbtipImgProjection(long abtno);

}
