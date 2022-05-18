package com.example.mapper;

import java.util.List;

import com.example.dto.InterestDTO;
import com.example.entity.InterestEntity;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

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
        public List<InterestEntity> selectListInterestInname(
                        @Param(value = "txt") String txt,
                        @Param(value = "start") int start,
                        @Param(value = "end") int end);

        // 카테고리로 검색, 페이지네이션
        @Select({ "SELECT * FROM (",
                        "	SELECT I.*,",
                        "		ROW_NUMBER() OVER (ORDER BY I.INCATEGORY DESC) ROWN ",
                        "	FROM ",
                        "		INTEREST I	",
                        "	WHERE ",
                        "		I.INCATEGORY LIKE '%' || #{txt} || '%'",
                        "	) ",
                        "       WHERE ROWN BETWEEN #{start} AND #{end} " })
        public List<InterestEntity> selectListInterestIncategory(
                        @Param(value = "txt") String txt,
                        @Param(value = "start") int start,
                        @Param(value = "end") int end);

        // 카테고리 개수 구하기
        @Select({ "SELECT",
                        "			COUNT(*) CNT ",
                        "		FROM ",
                        "			INTEREST I	",
                        "		WHERE ",
                        "			I.INCATEGORY LIKE '%' || #{txt} || '%'" })
        public long selectInterestIncategoryCount(
                        @Param(value = "txt") String txt);

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

        // 관심사 등록
        @Insert({ "INSERT INTO INTEREST( INCODE, INCATEGORY, INNAME, ",
                        "			INIMAGE, INIMAGESIZE, INIMAGETYPE, ",
                        "			INIMAGENAME ) ",
                        "		VALUES ( SEQ_INTEREST_NO.NEXTVAL, #{obj.incategory},#{obj.inname},",
                        "			#{obj.inimage, jdbcType=BLOB}, #{obj.inimagesize},#{obj.inimagetype},",
                        "			#{obj.inimagename})" })
        public int insertInterestOne(@Param(value = "obj") InterestDTO interest);

        // 관심사 1개 가져오기
        @Select({ "SELECT ",
                        "			INCODE, INCATEGORY, INNAME",
                        "		FROM ",
                        "			INTEREST",
                        "		WHERE ",
                        "			INCODE = #{code}" })
        public InterestDTO selectInterestOne(@Param(value = "code") long code);

        // 이미지 가져오기 (1개)
        @Results({
                        @Result(column = "INCODE", property = "incode"),
                        @Result(column = "INIMAGE", property = "inimage", jdbcType = JdbcType.BLOB, javaType = byte[].class)
        })

        @Select({ "SELECT ",
                        "			INCODE, INIMAGE, INIMAGESIZE, INIMAGETYPE, INIMAGENAME",
                        "		FROM ",
                        "			INTEREST",
                        "		WHERE ",
                        "			INCODE = #{code}" })
        public InterestDTO selectInterestImageOne(
                        @Param(value = "code") long code);

        // 관심사 수정
        @Update({
                        "<script>",
                        "UPDATE INTEREST SET INCATEGORY = #{obj.incategory}, ",
                        "INNAME = #{obj.inname} ",

                        "<if test='obj.inimage != null'>",
                        ", INIMAGE = #{obj.inimage} ",
                        ", INIMAGESIZE = #{obj.inimagesize} ",
                        ", INIMAGETYPE = #{obj.inimagetype} ",
                        ", INIMAGENAME = #{obj.inimagename} ",
                        "</if>",

                        "WHERE INCODE=#{obj.incode}",

                        "</script>"
        })
        public int updateInterestOne(@Param(value = "obj") InterestDTO interest);

        // 관심사 삭제
        @Delete({ "DELETE FROM INTEREST WHERE INCODE=#{code}" })
        public int deleteInterestOne(
                        @Param(value = "code") long code);

}
