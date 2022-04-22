package com.example.repository;

import com.example.entity.MemberEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRespository2 extends JpaRepository<MemberEntity, String> {

}
