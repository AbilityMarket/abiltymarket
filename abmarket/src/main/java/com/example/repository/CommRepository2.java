package com.example.repository;

import com.example.entity.CommEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommRepository2 extends JpaRepository<CommEntity, Long> {

}
