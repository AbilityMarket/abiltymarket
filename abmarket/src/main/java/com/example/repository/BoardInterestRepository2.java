package com.example.repository;

import com.example.entity.BoardInterest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardInterestRepository2 extends JpaRepository<BoardInterest, Long> {

}
