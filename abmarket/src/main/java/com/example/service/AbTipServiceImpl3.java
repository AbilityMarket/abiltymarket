package com.example.service;

import java.util.List;

import com.example.entity.AbTipEntity;
import com.example.repository.AbTipImageRepository3;
import com.example.repository.AbTipRepository3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AbTipServiceImpl3 implements AbTipService3 {

    @Autowired AbTipRepository3 abRepository3;

    @Autowired AbTipImageRepository3 abtiRepository3;

    // 팁 1개 삭제
    @Override
    public int deleteOneAbTip(long abtno) {
        try {
            abRepository3.deleteById(abtno);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    // 팁 등록
    @Override
    public int insertAbTip(AbTipEntity abtip) {
        try {
            abRepository3.save(abtip);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    // 팁 전체 갯수 구하기 (저장소에서 따로 설정)
    @Override
    public long selectCountAbTip(String text) {
        return 0;
    }

    // 팁 전체 목록 조회 (저장소에서 따로 설정)
    @Override
    public List<AbTipEntity> selectListAbTip(Pageable page, String abttitle) {
        try {
            return abRepository3.findByAbttitleContainingOrderByAbtnoDesc(page, abttitle);
        } catch (Exception e) {
            return null;
        }
    }

    // 팁 1개 조회
    @Override
    public AbTipEntity selectOneAbTip(long abtno) {
        try {
            return abRepository3.findById(abtno).orElse(null);
        } catch (Exception e) {
            return null;
        }
    }

    // 팁 1개 수정
    @Override
    public AbTipEntity updateOneAbTip(AbTipEntity abtip) {
        try {
            return abRepository3.save(abtip);
        } catch (Exception e) {
            return null;
        }
    }


    
}
