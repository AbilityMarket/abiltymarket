package com.example.repository;

import com.example.entity.AnswerEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository3 extends JpaRepository<AnswerEntity, Long> {
    
}
