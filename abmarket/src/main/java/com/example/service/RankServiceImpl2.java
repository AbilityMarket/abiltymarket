package com.example.service;

import java.util.List;

import com.example.entity.ChatViewEntity;
import com.example.entity.MemberEntity;
import com.example.entity.RankEntity;
import com.example.entity.RrrankEntity;
import com.example.repository.ChatViewRepository2;
import com.example.repository.RankRepository1;
import com.example.repository.RrrankRepository2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RankServiceImpl2 implements RankService2 {

    @Autowired
    RankRepository1 rankRepository1;

    @Autowired
    RrrankRepository2 rrrankRepository2;

    @Autowired
    ChatViewRepository2 chatViewRepository2;

    // 회원등급 조회하기
    @Override
    public RankEntity selectRank(String uid) {
        try {
            MemberEntity member = new MemberEntity();
            member.setUid(uid);

            RrrankEntity rrrank = rrrankRepository2.findByMember_uid(uid);
            System.out.println(rrrank);
            return rankRepository1.findById(rrrank.getRank().getRname()).orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 회원등급 업그레이드
    @Override
    public int upgradeRank(String uid) {
        try {
            // uid가 채팅 중인 리스트
            List<ChatViewEntity> list = chatViewRepository2.findByClickpersonOrWriter(uid, uid);
            System.out.println(list);
            // uid의 랭크 찾기
            RrrankEntity rrrank = rrrankRepository2.findByMember_uid(uid);
            // 처음 랭크
            String firstRank = rrrank.getRank().getRname();
            System.out.println("처음랭크" + firstRank);

            RankEntity rank = new RankEntity();
            Long count = 0L;

            // 거래완료가 몇 번 있었는가?
            for (ChatViewEntity chatview : list) {
                if (chatview.getChstate().equals("TDONE")) {
                    count++;
                }
            }
            // 5번 이상 1등급
            if (count >= 5L) {
                rank.setRname("1");
                rrrank.setRank(rank);
            }
            // 3번 이상 5번 미만 2등급
            else if (count < 5L && count >= 3L) {
                rank.setRname("2");
                rrrank.setRank(rank);
            }
            // 1번 이상 3번 미만 3등급
            else if (count < 3L && count >= 1L) {
                rank.setRname("3");
                rrrank.setRank(rank);
            }
            rrrankRepository2.save(rrrank);
            System.out.println("count" + count);

            // 바뀐 등급
            String updatedRank = rrrank.getRank().getRname();
            System.out.println("나중랭크" + updatedRank);
            // 랭크 변화가 없으면
            if (firstRank.equals(updatedRank)) {
                return 0;
            }
            // 랭크 변화가 있으면
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }

    }
}
