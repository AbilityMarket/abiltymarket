package com.example.repository;

import com.example.entity.MemberAddrEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemAddrRepository3 extends JpaRepository<MemberAddrEntity, Long> {
    
}
