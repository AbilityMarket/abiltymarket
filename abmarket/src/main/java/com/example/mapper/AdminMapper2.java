package com.example.mapper;

import java.util.List;

import com.example.dto.MemberDTO;
import com.example.entity.MemberEntity;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface AdminMapper2 {

        // 회원 목록 아이디로 검색, 페이지네이션
        @Select({ "SELECT * FROM (",
                        "			SELECT M.*,",
                        "				ROW_NUMBER() OVER (ORDER BY M.UID DESC) ROWN ",
                        "			FROM ",
                        "				MEMBER M	",
                        "			WHERE ",
                        "				M.UID LIKE '%' || #{txt} || '%'",
                        "		) ",
                        "		WHERE ROWN BETWEEN #{start} AND #{end} " })
        public List<MemberEntity> selectListMemberUid(
                        @Param(value = "txt") String txt,
                        @Param(value = "start") int start,
                        @Param(value = "end") int end);

        // 회원 목록 닉네임으로 검색, 페이지네이션
        @Select({ "SELECT * FROM (",
                        "			SELECT M.*,",
                        "				ROW_NUMBER() OVER (ORDER BY M.UID DESC) ROWN ",
                        "			FROM ",
                        "				MEMBER M	",
                        "			WHERE ",
                        "				M.UNICKNAME LIKE '%' || #{txt} || '%'",
                        "		) ",
                        "		WHERE ROWN BETWEEN #{start} AND #{end} " })
        public List<MemberEntity> selectListMemberUnickname(
                        @Param(value = "txt") String txt,
                        @Param(value = "start") int start,
                        @Param(value = "end") int end);

        // 페이지네이션용 개수구하기 아이디용
        @Select({ "SELECT",
                        "			COUNT(*) CNT ",
                        "		FROM ",
                        "			MEMBER M	",
                        "		WHERE ",
                        "			M.UID LIKE '%' || #{txt} || '%'" })
        public long selectMemberUidCount(
                        @Param(value = "txt") String txt);

        // 페이지네이션용 개수구하기 닉네임용
        @Select({ "SELECT",
                        "			COUNT(*) CNT ",
                        "		FROM ",
                        "			MEMBER M	",
                        "		WHERE ",
                        "			M.UNICKNAME LIKE '%' || #{txt} || '%'" })
        public long selectMemberUnicknameCount(
                        @Param(value = "txt") String txt);

        @Select({ "SELECT * FROM MEMBER WHERE UID=#{uid}" })
        public MemberDTO selectMemberImage(
                        @Param(value = "uid") String uid);
}
