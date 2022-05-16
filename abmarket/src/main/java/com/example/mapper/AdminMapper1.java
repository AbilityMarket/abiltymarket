package com.example.mapper;

import java.util.List;

import com.example.dto.InterestDTO;
import com.example.entity.InterestEntity;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface AdminMapper1 {

        // 관심사 이름 검색, 페이지네이션
        @Select({ "SELECT * FROM (",
                        "	SELECT I.*,",
                        "		ROW_NUMBER() OVER (ORDER BY I.INNAME DESC) ROWN ",
                        "	FROM ",
                        "		INTEREST I	",
                        "	WHERE ",
                        "		I.INNAME LIKE '%' || #{txt} || '%'",
                        "	) ",
                        "       WHERE ROWN BETWEEN #{start} AND #{end} " })
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
        public InterestDTO selectInterestImage(
                        @Param(value = "incode") Long incode);

        // 관심사 1개 가져오기
        @Select({ "SELECT ",
                        "			INCODE, INCATEGORY, INNAME",
                        "		FROM ",
                        "			INTEREST",
                        "		WHERE ",
                        "			INCODE = #{code}" })
        public InterestDTO selectInterestOne(@Param(value = "code") long code);

        // 관심사 수정
        @Update({
                        "<script>",
                        "UPDATE INTEREST SET INCATEGORY = #{obj.incategory}, ",
                        "INNAME = #{obj.inname} ",

                        "<if test='obj.iimage != null'>",
                        ", INIMAGE = #{obj.inimage} ",
                        ", INIMAGESIZE = #{obj.inimagesize} ",
                        ", INIMAGETYPE = #{obj.inimagetype} ",
                        ", INIMAGENAME = #{obj.inimagename} ",
                        "</if>",

                        "WHERE INCODE=#{obj.incode} AND UID=#{obj.uid}",
                        "</script>"
        })
        public int updateInterestOne(@Param(value = "obj") InterestDTO interest);

        // 관심사 삭제
        @Delete({ "DELETE FROM INTEREST WHERE INCODE=#{code} AND UID=#{uid}" })
        public int deleteInterestOne(
                        @Param(value = "code") long code,
                        @Param(value = "uid") String uid);

}
