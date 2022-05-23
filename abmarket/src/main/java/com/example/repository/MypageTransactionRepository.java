package com.example.repository;

import java.util.List;

import com.example.entity.MypageTransaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MypageTransactionRepository extends JpaRepository<MypageTransaction, Long> {
    List<MypageTransaction> findByClickperson(String uid);
}
