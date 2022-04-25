package com.example.repository;

import com.example.entity.ReportEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository1 extends JpaRepository<ReportEntity, Long> {

}
