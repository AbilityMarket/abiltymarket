package com.example.service;

import com.example.entity.ReportEntity;

import org.springframework.stereotype.Service;

@Service
public interface ReportService1 {

    // 신고글 작성하기
    public int insertReport(ReportEntity report);

    // 신고글 조회하기
    public ReportEntity selectReport(long repcode);

    // 신고글 수정하기
    public int updateReport(ReportEntity report);

    // 신고글 삭제하기
    public int deleteReport(long repcode);
}
