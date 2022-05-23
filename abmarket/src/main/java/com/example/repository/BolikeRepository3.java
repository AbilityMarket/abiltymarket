package com.example.repository;

import java.util.List;

import com.example.entity.BolikeEntity;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BolikeRepository3 extends JpaRepository<BolikeEntity, Long> {

    // 찜 총 갯수
    Long countByBoard_bno(Long bno);

    // 찜 유무 확인
    BolikeEntity findByMemberUidAndBoardBno(String uid, Long bno);

    // 찜 삭제
    Long deleteByMemberUidAndBoardBno(String uid, Long bno);

    // 찜 전체 목록 (회원 본인이 찜한 목록)
    List<BolikeEntity> findByMemberUid(Pageable page, String uid);

    List<BolikeEntity> findByMemberUid(String uid);

    // 다시 확인
    //List<BolikeEntity> findByMember_uidAndBoardOrderByBnoDesc(Pageable page, String uid, Long bno);

}
