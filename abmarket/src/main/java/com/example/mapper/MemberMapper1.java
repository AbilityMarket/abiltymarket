package com.example.mapper;

import com.example.dto.MemberDTO;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface MemberMapper1 {

        // Security 로그인
        // SELECT 컬럼명들 FROM 테이블명 WHERE 조건 AND 조건
        // @Select({
        // "SELECT UID, UPW, UROLE ",
        // " FROM MEMBER ",
        // " WHERE UID = #{uid} "
        // })
        // public MemberDTO memberLogin(
        // @Param(value = "uid") String uid);

}
