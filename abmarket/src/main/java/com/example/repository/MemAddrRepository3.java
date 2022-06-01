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

    // 회원별 주소 일괄 삭제
    List<MemberAddrEntity> deleteByUcodeIn(List<Long> ucode);

    // 회원별 대표주소 조회
    MemberAddrEntity findByMember_uidAndUchk(String userid, Long uchk);

    // 해당 회원 좌표 + km
    // ulongitude, ulatitude, ukm
    MemberAddrEntity findByMember_uidAndUlongitudeAndUlatitudeAndUkm(Double ulongitude, Double ulatitude, Long ukm, String userid);

}
