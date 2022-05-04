package com.example.repository;

import java.util.List;

import com.example.entity.BoardAndWriter;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardAndWriterRepository2 extends
                JpaRepository<BoardAndWriter, Long> {

        // 최근 4개 뽑아내기
        List<BoardAndWriter> findByBroleOrderByBregdateDesc(Pageable page, Long urole);
}
