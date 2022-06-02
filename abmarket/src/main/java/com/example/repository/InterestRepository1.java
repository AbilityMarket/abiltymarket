package com.example.repository;

import java.util.List;

import com.example.entity.InterestEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface InterestRepository1 extends JpaRepository<InterestEntity, Long> {

    @Query(value = "select * from (select *, ROW_NUMBER() OVER(PARTITION BY incategory) NO from interest) where NO=1;", nativeQuery = true)
    List<InterestEntity> findIncategory();

    List<InterestEntity> findByIncategory(String incategory);

    InterestEntity findByInname(String inname);

    List<InterestEntity> findByIncodeIn(Long[] no);
}
