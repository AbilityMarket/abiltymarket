package com.example.repository;

import com.example.entity.MeminterestEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemInterestRepository1 extends JpaRepository<MeminterestEntity, Long> {

}
