package com.example.service;

import com.example.entity.CommEntity;
import com.example.repository.CommRepository2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommServiceImpl2 implements CommService2 {

    @Autowired
    CommRepository2 cRepository2;

    // 댓글쓰기
    @Override
    public int insertComm(CommEntity comm) {
        try {
            cRepository2.save(comm);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
