package com.example.repository;

import java.util.List;

import com.example.entity.ReviewImageEntity;
import com.example.entity.ReviewImageEntityProjection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewImageRepository1 extends JpaRepository<ReviewImageEntity, Long> {

    List<ReviewImageEntityProjection> findByReview_revno(long revno);

}
