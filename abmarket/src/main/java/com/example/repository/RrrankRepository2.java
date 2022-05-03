package com.example.repository;

import com.example.entity.RrrankEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RrrankRepository2 extends JpaRepository<RrrankEntity, String> {

}
