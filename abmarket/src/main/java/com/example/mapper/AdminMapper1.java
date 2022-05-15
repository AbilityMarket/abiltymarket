package com.example.mapper;

import java.util.List;

import com.example.dto.InterestDTO1;
import com.example.entity.InterestEntity;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface AdminMapper1 {

        // 관심사 이름 검색, 페이지네이션
        @Select({ "SELECT * FROM (",
                        "			SELECT I.*,",
                        "				ROW_NUMBER() OVER (ORDER BY I.INNAME DESC) ROWN ",
                        "			FROM ",
                        "				INTEREST I	",
                        "			WHERE ",
                        "				I.INNAME LIKE '%' || #{txt} || '%'",
                        "		) ",
                        "		WHERE ROWN BETWEEN #{start} AND #{end} " })
        public List<InterestEntity> selectListInterest(
                        @Param(value = "txt") String txt,
                        @Param(value = "start") int start,
                        @Param(value = "end") int end);

        // 관심사 개수 구하기
        @Select({ "SELECT",
                        "			COUNT(*) CNT ",
                        "		FROM ",
                        "			INTEREST I	",
                        "		WHERE ",
                        "			I.INNAME LIKE '%' || #{txt} || '%'" })
        public long selectInterestInnameCount(
                        @Param(value = "txt") String txt);

        @Select({ "SELECT * FROM INTEREST WHERE INCODE=#{incode}" })
        public InterestDTO1 selectInterestImage(
                        @Param(value = "incode") long incode);
}
