package com.example.restcontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.entity.BoardAndWriter;
import com.example.entity.InterestEntity;
import com.example.entity.TradeRankView;
import com.example.service.MainService2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/main")
public class MainRestController2 {

    @Autowired
    MainService2 mainService2;

    // 도와주세요
    @RequestMapping(value = "/helpMe", method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> HelpmeGET() {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);

        try {
            Pageable pageable = PageRequest.of(0, 4);
            List<BoardAndWriter> list = mainService2.helpMe(pageable);
            if (list.size() > 0) {
                map.put("list", list);
                map.put("status", 200);
            }

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // 도와줄게요
    @RequestMapping(value = "/helpYou", method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> HelpYouGET() {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);

        try {
            Pageable pageable = PageRequest.of(0, 4);
            List<BoardAndWriter> list = mainService2.helpYou(pageable);
            // System.out.println(list);
            if (list.size() > 0) {
                map.put("list", list);
                map.put("status", 200);
            }

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // 인기키워드
    @RequestMapping(value = "/hotKeyword", method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> hotKeywordGET() {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);

        try {

            List<InterestEntity> list = mainService2.findHotKeyword();
            if (list.size() > 0) {
                map.put("list", list);
                map.put("status", 200);
            }

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // 능력자의 팁

    // 사연

    // 이달의 랭킹- 거래내역
    @RequestMapping(value = "/tradeRank", method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> tradeRankGET() {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);

        try {

            List<TradeRankView> list = mainService2.findtradeRank();

            if (list.size() > 0) {
                map.put("list", list);
                map.put("status", 200);
            }

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

}
