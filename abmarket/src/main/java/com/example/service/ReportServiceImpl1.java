package com.example.service;

import com.example.entity.ReportEntity;
import com.example.repository.ReportRepository1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl1 implements ReportService1 {

    @Autowired
    ReportRepository1 repRepository1;

    // 신고글 작성하기
    @Override
    public int insertReport(ReportEntity report) {
        try {
            repRepository1.save(report);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 신고글 조회하기
    @Override
    public ReportEntity selectReport(long repcode) {
        try {
            return repRepository1.findById(repcode).orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 신고글 수정하기
    @Override
    public int updateReport(ReportEntity report) {
        try {
            repRepository1.save(report);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 신고글 삭제하기
    @Override
    public int deleteReport(long repcode) {
        try {
            repRepository1.deleteById(repcode);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
