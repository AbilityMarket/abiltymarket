package com.example.repository;

import java.util.List;

import com.example.entity.MemberAddrEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemAddrRepository3 extends JpaRepository<MemberAddrEntity, Long> {

    // 회원별 주소 1개 조회
    MemberAddrEntity findByUcodeAndMember_uid(Long ucode, String userid);

    // 회원별 주소 전체 조회
    List<MemberAddrEntity> findAllByMember_uid(String userid);

}
