package com.example.repository;

import java.util.List;

import com.example.entity.BoardEntity;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository1 extends JpaRepository<BoardEntity, Long> {

    List<BoardEntity> findByBtitleContainingOrderByBnoDesc(Pageable page, String btitle);

}
