package com.example.repository;

import com.example.entity.ChatroomEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatroomRepository2 extends JpaRepository<ChatroomEntity, Long> {

}
