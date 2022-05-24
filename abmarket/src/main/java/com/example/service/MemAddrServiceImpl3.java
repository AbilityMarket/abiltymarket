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

import com.example.entity.MemberAddrEntity;
import com.example.repository.MemAddrRepository3;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class MemAddrServiceImpl3 implements MemAddrService3 {

    @Autowired
    MemAddrRepository3 memAddrRepository3;

    // 주소 api 호출
    @Override
    public String getKakaoApiFromMemAddr(String roadFullAddr) {

        String apiKey = "발급받은 API 키";
        String apiUrl = "https://dapi.kakao.com/v2/local/search/address.json";
        String jsonString = null; //json string -> map 으로 변환 시켜야 됨
    
        try {
            roadFullAddr = URLEncoder.encode(roadFullAddr, "UTF-8");
    
            String addr = apiUrl + "?query=" + roadFullAddr;
    
            URL url = new URL(addr);
            URLConnection urlConn = url.openConnection();
            urlConn.setRequestProperty("Authorization", "KakaoAK " + apiKey);
    
            BufferedReader reader = null;
            reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream(), "UTF-8"));
            StringBuffer docJson = new StringBuffer();
    
            String line;
    
            while ((line=reader.readLine()) != null) {
                docJson.append(line);
            }
    
            jsonString = docJson.toString();
            reader.close();
    
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    // 주소 api Map으로 변환
    @Override
    public HashMap<String, String> getXYMapFromJsonStr(String jsonString) {
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, String> XYMap = new HashMap<String, String>();

        try {
            TypeReference<Map<String, Object>> typeRef 
                = new TypeReference<Map<String, Object>>(){};
            Map<String, Object> jsonMap = mapper.readValue(jsonString, typeRef);
    
            @SuppressWarnings("unchecked")
            List<Map<String, String>> docList 
                =  (List<Map<String, String>>) jsonMap.get("documents");	
    
            Map<String, String> adList = (Map<String, String>) docList.get(0);
            XYMap.put("x",adList.get("x"));
            XYMap.put("y", adList.get("y"));
    
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
    public int updateMemAddr(MemberAddrEntity memAddrEnt) {
        try {
            memAddrRepository3.save(memAddrEnt);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }
    
}
