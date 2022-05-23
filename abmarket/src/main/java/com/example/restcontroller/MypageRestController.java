package com.example.restcontroller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.entity.BoardAndWriter;
import com.example.entity.BoardEntity;
import com.example.entity.BoardProjection;
import com.example.entity.ChatViewEntity;
import com.example.entity.MypageTransaction;
import com.example.jwt.JwtUtil;
import com.example.repository.BoardRepository1;
import com.example.repository.ChatViewRepository2;
import com.example.repository.MypageTransactionRepository;
import com.example.service.MainService2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/mypage")
public class MypageRestController {

	@Autowired
	JwtUtil jwtUtil;

	@Autowired
	MainService2 mainService2;

	@Autowired
	ChatViewRepository2 chatViewRepository2;

	@Autowired
	BoardRepository1 boardRepository1;

	@Autowired
	MypageTransactionRepository mypageTransactionRepository;

	// 거래 내역 조회
	@RequestMapping(value = "/transactionHistory", method = { RequestMethod.GET }, consumes = {
			MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public Map<String, Object> HelpmeGET(
			@RequestHeader(name = "token") String token) {
		Map<String, Object> map = new HashMap<>();
		map.put("status", 0);

		try {
			String uid = jwtUtil.extractUsername(token);
			List<MypageTransaction> list = mypageTransactionRepository.findByClickperson(uid);

			if (list.size() > 0) {
				map.put("status", 200);
				map.put("list", list);
			}

		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", -1);
		}
		return map;
	}
}