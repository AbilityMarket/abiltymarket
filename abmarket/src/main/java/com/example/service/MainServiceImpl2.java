package com.example.service;

import java.util.ArrayList;
import java.util.List;

import com.example.entity.BoardAndWriter;
import com.example.entity.HotKeyword;
import com.example.entity.InterestEntity;
import com.example.entity.TradeRankView;
import com.example.repository.BoardAndWriterRepository2;
import com.example.repository.BoardInterestRepository2;
import com.example.repository.HotKeywordRepository2;
import com.example.repository.InterestRepository1;
import com.example.repository.TradeRankRepository2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MainServiceImpl2 implements MainService2 {

    @Autowired
    BoardAndWriterRepository2 boardAndWriterRepository2;

    @Autowired
    BoardInterestRepository2 boardInterestRepository2;

    @Autowired
    HotKeywordRepository2 hotKeywordRepository2;

    @Autowired
    InterestRepository1 interestRepository1;

    @Autowired
    TradeRankRepository2 tradeRankRepository2;

    // 도와주세요
    @Override
    public List<BoardAndWriter> helpMe(Pageable page) {
        try {
            List<BoardAndWriter> list = boardAndWriterRepository2.findByBroleOrderByBregdateDesc(page, 1L);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    // 도와줄게
    @Override
    public List<BoardAndWriter> helpYou(Pageable page) {
        try {
            List<BoardAndWriter> list = boardAndWriterRepository2.findByBroleOrderByBregdateDesc(page, 2L);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 인기 카테고리(게시판 카테고리 설정 많은 순서)
    @Override
    public List<InterestEntity> findHotKeyword() {
        try {
            List<HotKeyword> list = hotKeywordRepository2.findTop15ByOrderByCount();
            List<InterestEntity> interest = new ArrayList<InterestEntity>();
            if (list.size() > 0) {
                for (HotKeyword hotKeyword : list) {
                    interest.add(interestRepository1.findById(hotKeyword.getIncode()).orElse(null));
                }
            }
            return interest;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 이달의 랭킹 - 거래완료 랭크 높은 순
    @Override
    public List<TradeRankView> findtradeRank() {
        try {
            return tradeRankRepository2.findTop5ByOrderByPlusDesc();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
