package com.example.repository;

import com.example.entity.BoardEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository1 extends JpaRepository<BoardEntity, Long> {

}
