package com.example.service;

import java.util.List;

import com.example.entity.BoardAndWriter;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface MainService2 {

    // 도와주세요
    public List<BoardAndWriter> helpMe(Pageable page);

    // 도와줄게용~
    public List<BoardAndWriter> helpYou(Pageable page);
}
