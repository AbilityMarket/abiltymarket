package com.example.mapper;

import java.util.List;

import com.example.dto.InterestDTO1;
import com.example.entity.InterestEntity;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface AdminMapper1 {

    // public List<InterestEntity> selectListMemberUid(
    // @Param(value = "txt") String txt,
    // @Param(value = "start") int start,
    // @Param(value = "end") int end);

    // // 회원 목록 닉네임으로 검색, 페이지네이션
    // @Select({ "SELECT * FROM (",
    // " SELECT M.*,",
    // " ROW_NUMBER() OVER (ORDER BY M.UID DESC) ROWN ",
    // " FROM ",
    // " MEMBER M ",
    // " WHERE ",
    // " M.UNICKNAME LIKE '%' || #{txt} || '%'",
    // " ) ",
    // " WHERE ROWN BETWEEN #{start} AND #{end} " })

    // @Select({ "SELECT * FROM INTEREST WHERE INCODE=#{incode}" })
    // public InterestDTO1 selectInterestImage(
    // @Param(value = "incode") long incode);
}
