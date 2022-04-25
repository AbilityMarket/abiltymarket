package com.example.repository;

import com.example.entity.ReviewEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository1 extends JpaRepository<ReviewEntity, Long> {

}
