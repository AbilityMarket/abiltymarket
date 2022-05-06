package com.example.repository;

import java.util.List;

import com.example.entity.TradeRankView;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeRankRepository2 extends JpaRepository<TradeRankView, String> {
    List<TradeRankView> findTop5ByOrderByPlusDesc();
}
