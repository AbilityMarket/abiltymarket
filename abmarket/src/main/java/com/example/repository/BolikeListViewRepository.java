package com.example.repository;

import com.example.entity.BolikeListView;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BolikeListViewRepository extends JpaRepository<BolikeListView, Long> {

}
