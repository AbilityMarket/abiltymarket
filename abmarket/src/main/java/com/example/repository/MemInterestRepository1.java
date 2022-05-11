package com.example.repository;

import java.util.List;

import javax.transaction.Transactional;

import com.example.entity.MeminterestEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemInterestRepository1 extends JpaRepository<MeminterestEntity, Long> {

    @Transactional

    // 회원 관심사 등록 체크해제
    int deleteByMember_uidAndInterest_incode(String uid, long incode);

    // 관심사 설정 여부( 0 -> 미설정, 1 -> 설정)
    List<MeminterestEntity> findByMialertAndMember_uid(long mialert, String uid);
}
