package com.example.service;

import com.example.entity.MeminterestEntity;
import com.example.repository.MemInterestRepository1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemInterestServiceImpl1 implements MemInterestService1 {

    @Autowired
    MemInterestRepository1 memIntRepository1;

    @Override
    public int insertalert(MeminterestEntity mialert) {
        try {
            memIntRepository1.save(mialert);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int deletealert(long micode) {
        try {
            memIntRepository1.deleteById(micode);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
