package com.example.service;

import java.util.List;

import com.example.entity.BoardAndWriter;
import com.example.repository.BoardAndWriterRepository2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MainServiceImpl2 implements MainService2 {

    @Autowired
    BoardAndWriterRepository2 boardAndWriterRepository2;

    // 도와주세요
    @Override
    public List<BoardAndWriter> helpMe(Pageable page) {
        try {
            List<BoardAndWriter> list = boardAndWriterRepository2.findByBroleOrderByBregdateDesc(page, 1L);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    // 도와줄게
    @Override
    public List<BoardAndWriter> helpYou(Pageable page) {
        try {
            List<BoardAndWriter> list = boardAndWriterRepository2.findByBroleOrderByBregdateDesc(page, 2L);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
