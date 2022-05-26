package com.example.restcontroller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.entity.MemberAddrEntity;
import com.example.entity.MemberEntity;
import com.example.jwt.JwtUtil;
import com.example.repository.MemAddrRepository3;
import com.example.service.MemAddrService3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/memaddr")
public class MemAddrRestController3 {

	// 토큰
	@Autowired
	JwtUtil jwtUtil;

	@Autowired
	MemAddrService3 memAddrService3;

	@Autowired
	MemAddrRepository3 memAddrRepository3;

	// 127.0.0.1:9090/ROOT/api/memaddr/getmapaddr
	@RequestMapping(value = { "/getmapaddr" }, method = { RequestMethod.GET }, consumes = {
			MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public void getMapAddr(@RequestParam HashMap<String, String> paramMap) {
		// System.out.println(paramMap);

		String roadFullAddr = paramMap.get("paramMap");
		// System.out.println(roadFullAddr);
		String jsonString = memAddrService3.getKakaoApiFromMemAddr(roadFullAddr);
		// System.out.println(jsonString);

		// x = 경도(longitude), y = 위도(latitude)
		HashMap<String, String> XYMap = memAddrService3.getXYMapFromJsonStr(jsonString);
		paramMap.put("latitude", XYMap.get("y"));
		paramMap.put("longitude", XYMap.get("x"));
		System.out.println(paramMap);
	}

	// 주소 등록 (토큰X)
	// address_name, longitude(X), latitude(Y)
	// uaddress, ulongitude, ulatitude
	// 127.0.0.1:9090/ROOT/api/memaddr/insertmemaddr?uid=~~~
	@RequestMapping(value = { "/insertmemaddr" }, method = { RequestMethod.POST }, consumes = {
			MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public Map<String, Object> insertMemAddr(
			@RequestParam(name = "uid") String uid,
			@ModelAttribute MemberAddrEntity memAddrEnt) {

		Map<String, Object> map = new HashMap<>();
		map.put("status", 0);

		try {
			// 주소 등록 일자 추가
			LocalDateTime insertNow = LocalDateTime.now();
			memAddrEnt.setUregdate(insertNow);
			MemberEntity member = new MemberEntity();
			member.setUid(uid);
			memAddrEnt.setMember(member);
			// System.out.println(insertNow);

			int ret = memAddrService3.insertMemAddr(memAddrEnt);
			if (ret == 1) {
				map.put("result", "등록완료!");
				map.put("status", 200);
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", -1);
		}
		return map;
	}

	// 주소 수정 (토큰O)
	// address_name, longitude(X), latitude(Y)
	// uaddress, ulongitude, ulatitude
	// 127.0.0.1:9090/ROOT/api/memaddr/updatememaddr
	@RequestMapping(value = { "/updatememaddr" }, method = { RequestMethod.PUT }, consumes = {
			MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public Map<String, Object> updateMemAddr(
			@RequestHeader(name = "token") String token,
			@ModelAttribute MemberAddrEntity memAddrEnt) {

		Map<String, Object> map = new HashMap<>();

		try {
			// 토큰 필요함(토큰 추출)
			String userid = jwtUtil.extractUsername(token);
			System.out.println("RequestMapping username : " + userid);
			System.out.println("here" + memAddrEnt);

			MemberAddrEntity memAddrEntity = memAddrRepository3.getById(memAddrEnt.getUcode());
			System.out.println("기존주소===" + memAddrEntity.getUaddress());

			// memAddrEntity.setUkm(memAddrEnt.getUkm()); // km
			memAddrEntity.setUaddress(memAddrEnt.getUaddress()); // 주소
			memAddrEntity.setUlatitude(memAddrEnt.getUlatitude()); // 위도
			memAddrEntity.setUlongitude(memAddrEnt.getUlongitude()); // 경도
			LocalDateTime insertNow = LocalDateTime.now(); // 주소 등록 일자 추가
			memAddrEntity.setUregdate(insertNow);
			System.out.println("수정주소===" + memAddrEntity.getUaddress());

			int ret = memAddrService3.updateOneMemAddr(memAddrEntity);
			if (ret == 1) {
				map.put("result", "수정완료!");
				map.put("status", 200);
			} else {
				map.put("status", 0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", -1);
		}
		return map;
	}

	// 주소 1개 조회 (대표주소)
	// 해당 회원 주소 조회
	// 127.0.0.1:9090/ROOT/api/memaddr/selonememaddr
	@RequestMapping(value = { "/selonememaddr" }, method = { RequestMethod.GET }, consumes = {
			MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public Map<String, Object> selectOneMemAddr(
			@RequestHeader(name = "token") String token) {

		Map<String, Object> map = new HashMap<>();
		map.put("status", 0);

		try {
			// 토큰 필요함(토큰 추출)
			String userid = jwtUtil.extractUsername(token);
			System.out.println("RequestMapping username : " + userid);

			MemberAddrEntity memAddrEnt = memAddrRepository3.findByMember_uidAndUchk(userid, 1L);
			if (memAddrEnt != null) {
				map.put("status", 200);
				map.put("memAddrEnt", memAddrEnt);
			}

		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", -1);
		}
		return map;
	}

	// 해당 회원 주소 전체 조회
	// 127.0.0.1:9090/ROOT/api/memaddr/listmemaddr
	@RequestMapping(value = { "/listmemaddr" }, method = { RequestMethod.GET }, consumes = {
			MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public Map<String, Object> selectMemAddr(
			@RequestHeader(name = "token") String token) {

		Map<String, Object> map = new HashMap<>();
		map.put("status", 0);

		try {
			// 토큰 필요함(토큰 추출)
			String userid = jwtUtil.extractUsername(token);
			System.out.println("RequestMapping username : " + userid);

			List<MemberAddrEntity> list = memAddrService3.selectListMemAddr(userid);

			map.put("status", 200);
			map.put("list", list);

		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", -1);
		}
		return map;
	}

	// 해당 회원 주소 1개 삭제
	// 127.0.0.1:9090/ROOT/api/memaddr/deleteoneaddr
	@RequestMapping(value = { "/deleteoneaddr" }, method = { RequestMethod.DELETE }, consumes = {
			MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public Map<String, Object> deleteOneAddr(
			@RequestHeader(name = "token") String token,
			@RequestParam(name = "ucode") Long ucode) {

		Map<String, Object> map = new HashMap<>();
		map.put("status", 0);

		try {
			// 토큰 필요함(토큰 추출)
			String userid = jwtUtil.extractUsername(token);
			System.out.println("RequestMapping username : " + userid);

			int ret = memAddrService3.deleteOneMemAddr(ucode);
			if (ret == 1) {
				map.put("result", "삭제완료!");
				map.put("status", 200);
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", -1);
		}
		return map;
	}

	// 해당 회원 주소 일괄 삭제
	// 127.0.0.1:9090/ROOT/api/memaddr/deletelistaddr
	@RequestMapping(value = { "/deletelistaddr" }, method = { RequestMethod.DELETE }, consumes = {
			MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public Map<String, Object> deleteListAddr(
			@RequestHeader(name = "token") String token,
			@RequestParam(name = "ucode") List<Long> ucode) {

		Map<String, Object> map = new HashMap<>();

		try {
			// 토큰 필요함(토큰 추출)
			String userid = jwtUtil.extractUsername(token);
			System.out.println("RequestMapping username : " + userid);

			List<MemberAddrEntity> list = memAddrService3.deleteListMemAddr(ucode);
			// System.out.println(list); // 없으면 []
			if (list.size() != 0) {
				map.put("result", "일괄삭제완료!");
				map.put("status", 200);
			} else {
				map.put("result", "존재하지않는주소!");
				map.put("status", 0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", -1);
		}
		return map;
	}

}
