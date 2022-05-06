package com.example.service;

import java.util.List;

import com.example.entity.BoardAndWriter;
import com.example.entity.InterestEntity;
import com.example.entity.TradeRankView;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface MainService2 {

    // 도와주세요
    public List<BoardAndWriter> helpMe(Pageable page);

    // 도와줄게용~
    public List<BoardAndWriter> helpYou(Pageable page);

    // 인기키워드
    public List<InterestEntity> findHotKeyword();

    // 이달의 랭킹 - 거래완료 랭크 높은 순
    public List<TradeRankView> findtradeRank();
}
