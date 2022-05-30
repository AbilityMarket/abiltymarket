package com.example.repository;

import java.util.List;

import com.example.entity.MemIntAndBodAndBodInt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemIntAndBodAndBodIntRepository3 extends JpaRepository <MemIntAndBodAndBodInt, Long> {

    // 회원별 관심사 중 해당되는 게시판 전체 조회 (뷰 생성)
    List<MemIntAndBodAndBodInt> findByMemberUid(String userid);

    // 해당 회원 관심사에 포함 된 게시글 구매 판매 조회
    List<MemIntAndBodAndBodInt> findByMemberUidAndBrole(String userid, Long brole);
    
}
