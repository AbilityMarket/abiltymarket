package com.example.repository;

import com.example.entity.AlertEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertRepository3 extends JpaRepository<AlertEntity, Long> {
    
}
