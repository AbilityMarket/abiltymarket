package com.example.repository;

import com.example.entity.ChatEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository2 extends JpaRepository<ChatEntity, Long> {

}
