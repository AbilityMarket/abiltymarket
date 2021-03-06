package com.example.repository;

import java.util.List;

import com.example.entity.CommEntity;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommRepository2 extends JpaRepository<CommEntity, Long> {

    List<CommEntity> findByBoard_bnoOrderByCoregdateDesc(Pageable page, Long bno);

    List<CommEntity> findByBoard_bno(Long bno);

    CommEntity findByConoAndMember_uid(Long cono, String uid);

    Long countByBoard_bno(Long bno);
}
