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
    
    // 관심사 설정 유무 확인
    int countByMember_uidAndMialert(String uid, long mialert);
    
    MeminterestEntity findByMicodeAndMember_uid(long micode, String uid);
    
    // 관심사별 회원 조회
    // findAllBy & In 사용 
    List<MeminterestEntity> findAllByInterest_incodeIn(List<Long> incode);
    
}
