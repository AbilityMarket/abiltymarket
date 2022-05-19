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

        // 구매메인
        // 구매 1L, 카테고리, 카테고리이름,
        List<BoardAndWriter> findByBroleAndIncategoryAndInnameOrderByBregdateDesc(Pageable page, Long brole,
                        String incategory, String inname);

        // 페이지네이션용 개수구하기
        Long countByBroleAndIncategoryAndInnameOrderByBregdateDesc(Long brole, String incategory, String inname);

        // 구매 1L, 카테고리로 찾기
        List<BoardAndWriter> findByBroleAndIncategoryOrderByBregdateDesc(Pageable page, Long brole,
                        String incategory);

        // 페이지네이션용 카테고리로 검색 개수구하기
        Long countByBroleAndIncategoryOrderByBregdateDesc(Long brole, String incategory);

        // 혹시나 하고 만들어봄
        // bno, uid, incode
        List<BoardAndWriter> findByBnoAndUidAndIncode(Long bno, String uid, Long incode);

}
