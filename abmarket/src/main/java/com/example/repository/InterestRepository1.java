package com.example.repository;

import com.example.entity.InterestEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterestRepository1 extends JpaRepository<InterestEntity, Long> {

}
