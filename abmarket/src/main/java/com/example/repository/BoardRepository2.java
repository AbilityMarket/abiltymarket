package com.example.repository;

import java.util.Map;

import com.example.entity.BoardEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository2 extends JpaRepository<BoardEntity, Long> {
    @Query(value = "select count(btag), btag from board group by btag", nativeQuery = true)
    public Map<String, Object> findHotKeyword();
}
