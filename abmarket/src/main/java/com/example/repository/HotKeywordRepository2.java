package com.example.repository;

import java.util.List;

import com.example.entity.HotKeyword;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotKeywordRepository2 extends JpaRepository<HotKeyword, Long> {
    List<HotKeyword> findTop20ByOrderByCount();
}
