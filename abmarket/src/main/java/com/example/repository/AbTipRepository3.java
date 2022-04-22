package com.example.repository;

import com.example.entity.AbTipEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AbTipRepository3 extends JpaRepository<AbTipEntity, Long> {
    
}
