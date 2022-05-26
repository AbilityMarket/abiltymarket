package com.example.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import com.example.entity.MemberAddrEntity;
import com.example.repository.MemAddrRepository3;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MemAddrServiceImpl3 implements MemAddrService3 {

    @Autowired
    MemAddrRepository3 memAddrRepository3;

    // 주소 api 호출
    @Override
    public String getKakaoApiFromMemAddr(String roadFullAddr) {

        String apiKey = "eddc9574385a3fb5f33707a8d3bfcb98";
        String apiUrl = "https://dapi.kakao.com/v2/local/search/address.json";
        String jsonString = null; //json string -> map 으로 변환 시켜야 됨
    
        try {
            // 경남 창원시 마산회원구 마산역광장로 2 (석전동) -> 인코딩 해야 됨
            roadFullAddr = URLEncoder.encode(roadFullAddr, "UTF-8");
    
            String addr = apiUrl + "?query=" + roadFullAddr;
    
            // url 객체 생성
            URL url = new URL(addr);

            URLConnection urlConn = url.openConnection();
            urlConn.setRequestProperty("Authorization", "KakaoAK " + apiKey);
    
            BufferedReader reader = null;
            // 문자열을 읽을 스트림을 생성
            reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream(), "UTF-8"));
            
            // 문자열을 읽기 위한 임시변수를 생성
            StringBuffer docJson = new StringBuffer();
            
            String line;
            //버퍼에 있는 정보를 하나의 문자열로 변환
            while ((line=reader.readLine()) != null) {
                docJson.append(line);
            }
    
            jsonString = docJson.toString(); //읽은 내용을 String으로 변환
            reader.close(); //연결 종료
    
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    // 주소 json string -> Map 변환하고 x,y 값 얻기
    @Override
    public HashMap<String, String> getXYMapFromJsonStr(String jsonString) {

        ObjectMapper mapper = new ObjectMapper();

        HashMap<String, String> XYMap = new HashMap<String, String>();
        try {
            TypeReference<Map<String, Object>> typeRef = new TypeReference<Map<String, Object>>(){};

            Map<String, Object> jsonMap = mapper.readValue(jsonString, typeRef);
    
            @SuppressWarnings("unchecked")
            List<Map<String, String>> documList = (List<Map<String, String>>) jsonMap.get("documents");	
    
            Map<String, String> addrList = (Map<String, String>) documList.get(0);
            XYMap.put("x",addrList.get("x"));
            XYMap.put("y", addrList.get("y"));
    
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return XYMap;
    }
    
    // 주소 1개 등록
    @Override
    public int insertMemAddr(MemberAddrEntity memAddrEnt) {
        try {
            memAddrRepository3.save(memAddrEnt);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    // 주소 1개 수정
    @Override
    public int updateOneMemAddr(MemberAddrEntity memAddrEnt) {
        try {
            memAddrRepository3.save(memAddrEnt);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    // 주소 1개 조회
    @Override
    public MemberAddrEntity selectOneMemAddr(Long ucode, String userid) {
        try {
            return memAddrRepository3.findByUcodeAndMember_uid(ucode, userid);
        } catch (Exception e) {
            return null;
        }
    }

    // 회원별 전체 주소 조회
    @Override
    public List<MemberAddrEntity> selectListMemAddr(String userid) {
        try {
            return memAddrRepository3.findAllByMember_uid(userid);
        } catch (Exception e) {
            return null;
        }
    }

    // 주소 1개 삭제
    @Override
    public int deleteOneMemAddr(Long ucode) {
        try {
            memAddrRepository3.deleteById(ucode);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }
    
    // 주소 일괄 삭제
    @Override
    @Transactional //저장소에서 삭제 설정시 추가
    public List<MemberAddrEntity> deleteListMemAddr(List<Long> ucode) {
        try {
            return memAddrRepository3.deleteByUcodeIn(ucode);
        } catch (Exception e) {
            return null;
        }
    }


    
}
