package com.example.repository;

import com.example.entity.RankEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RankRepository1 extends JpaRepository<RankEntity, String> {

}
