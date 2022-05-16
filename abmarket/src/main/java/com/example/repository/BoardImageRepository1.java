package com.example.repository;

import java.util.List;

import com.example.entity.BoardImageEntity;
import com.example.entity.BoardImageEntityProjection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardImageRepository1 extends JpaRepository<BoardImageEntity, Long> {

    List<BoardImageEntityProjection> findByBoard_bno(long bno);

    BoardImageEntity findTop1ByBoard_bno(long bno);

}
