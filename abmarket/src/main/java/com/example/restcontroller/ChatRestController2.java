package com.example.restcontroller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.entity.AlertEntity;
import com.example.entity.ChatEntity;
import com.example.entity.ChatViewEntity;
import com.example.entity.ChatroomEntity;
import com.example.entity.MemberEntity;
import com.example.jwt.JwtUtil;
import com.example.repository.ChatRepository2;
import com.example.repository.ChatViewRepository2;
import com.example.repository.ChatroomRepository2;
import com.example.service.AlertServiceImpl3;
import com.example.service.ChatService2;
import com.example.service.RankService2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/api/chat")
public class ChatRestController2 {

    // 토큰 가져오기
    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    ChatService2 cService2;

    @Autowired
    ChatroomRepository2 chatroomRepository2;

    @Autowired
    ChatRepository2 chatRepository2;

    @Autowired
    RankService2 rankService2;

    @Autowired
    ResourceLoader resLoader;

    // 이미지 가져오기
    // 127.0.0.1:9090/ROOT/api/chat/image?chno=8
    @GetMapping(value = "/image") // url 주소생성
    public ResponseEntity<byte[]> imageGET(
            @RequestParam(name = "chno") long chno)
            throws IOException {

        // 이미지명, 이미지크기, 이미지종류, 이미지데이터
        ChatEntity chatImage = chatRepository2.findById(chno).orElse(null);
        // System.out.println(chatImage.getChimagename());

        // 이미지가 있을때
        if (chatImage.getChimagesize() > 0) { // 첨부한 파일 존재
            HttpHeaders headers = new HttpHeaders();

            if (chatImage.getChimagetype().equals("image/jpeg")) {
                headers.setContentType(MediaType.IMAGE_JPEG);
            } else if (chatImage.getChimagetype().equals("image/png")) {
                headers.setContentType(MediaType.IMAGE_PNG);
            } else if (chatImage.getChimagetype().equals("image/gif")) {
                headers.setContentType(MediaType.IMAGE_GIF);
            }
            // 이미지 byte[], headers, HttpStatus.Ok
            ResponseEntity<byte[]> response = new ResponseEntity<>(chatImage.getChimage(),
                    headers, HttpStatus.OK);
            return response;
        } else { // 이미지 없을때
            InputStream is = resLoader
                    .getResource("classpath:/static/images/default.jpg")
                    .getInputStream();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);

            ResponseEntity<byte[]> response = new ResponseEntity<>(is.readAllBytes(),
                    headers, HttpStatus.OK);
            return response;
        }
    }

    // 마지막 채팅 가져오기
    // /ROOT/api/chat/findLastChat?crno=
    @RequestMapping(value = "/findLastChat", method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> findLastChat(
            @RequestParam(name = "crno") Long crno) {

        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            // 토큰에서 현재 사용자 아이디 뽑아내기
            // String uid = jwtUtil.extractUsername(token);
            // System.out.println(crno);

            // ChatEntity chat = new ChatEntity();
            ChatEntity chat = chatRepository2.findTop1ByChatroom_crnoOrderByChregdateDesc(crno);
            // List<ChatViewEntity> list = cService2.selectChatRoomList(uid);
            if (chat != null) {
                map.put("status", 200);
                map.put("result", chat.getChcontent());
            }

        } catch (Exception e) {
            map.put("status", -1);
            e.printStackTrace();
        }
        return map;
    }

    // 채팅방있나 확인하고 없으면 만들기
    @RequestMapping(value = "/checkRoom", method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> checkRoom(
            @RequestHeader String token,
            @RequestParam(name = "bno") Long bno) {
        Map<String, Object> map = new HashMap<>();
        try {
            // 토큰에서 현재 사용자 아이디 뽑아내기
            String uid = jwtUtil.extractUsername(token);
            // System.out.println(uid);

            // 채팅방 유무 확인하기
            int chatroom = cService2.searchChatRoom(uid, bno);

            // 채팅방이 있으면 0리턴
            if (chatroom == 0) {
                map.put("status", 0);
                map.put("result", "채팅방 있음");
            }
            // 채팅방이 없으면 채팅방 만들기
            else if (chatroom == 1) {
                Map<String, Object> ret = cService2.createChatRoom2(uid, bno);
                // System.out.println(ret);
                // 저장이 제대로 되는 경우
                if (ret.get("status").equals(1)) {
                    map.put("chatroom", ret.get("chatroom"));
                    map.put("status", 200);
                    map.put("result", "채팅방생성");
                }
                // 본인 글일 경우
                else if (ret.get("status").equals(3)) {
                    map.put("status", 3);
                    map.put("result", "본인 글임");
                }
                // userid나 bno가 없는게 전달될 경우
                else if (ret.get("status").equals(2)) {
                    map.put("status", 2);
                    map.put("result", "userid나 bno없음");
                }
            } else {
                map.put("status", -1);
            }
        } catch (Exception e) {
            map.put("status", -1);
            e.printStackTrace();
        }
        return map;
    }

    // /ROOT/api/chat/selectlist
    // 채팅방 리스트 불러오기
    @RequestMapping(value = "/selectlist", method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> selectlistChatRoom(
            @RequestHeader String token) {

        Map<String, Object> map = new HashMap<>();
        try {
            // 토큰에서 현재 사용자 아이디 뽑아내기
            String uid = jwtUtil.extractUsername(token);

            List<ChatViewEntity> list = cService2.selectChatRoomList(uid);
            if (list.size() > 0) {
                map.put("status", 200);
                map.put("result", list);
            } else {
                map.put("status", 0);
                map.put("result", "채팅방없음");
            }

        } catch (Exception e) {
            map.put("status", -1);
            e.printStackTrace();
        }
        return map;
    }

    // 채팅 입력하기
    // 127.0.0.1:9090/ROOT/api/chat/sendMessage?crno=1
    @RequestMapping(value = "/sendMessage", method = { RequestMethod.POST }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> sendMessage(
            @RequestHeader(name = "token") String token,
            @ModelAttribute ChatEntity chat,
            @RequestParam(name = "crno") Long crno,
            @RequestParam(name = "file", required = false) MultipartFile file) {

        Map<String, Object> map = new HashMap<>();
        try {

            // 토큰에서 현재 사용자 아이디 뽑아내기
            String uid = jwtUtil.extractUsername(token);

            // 채팅방 번호로 조회하기
            ChatroomEntity chatroom = chatroomRepository2.findById(crno).orElse(null);

            // 먼저 말을 거는 사람 chatroom2.getMember().getUid());
            // 지금 로그인 한 사람 uid);
            // 게시글 쓴 사람, 채팅 받는 사람" chatroom2.getBoard().getMember().getUid());

            // 로그인 한 사람과 대화하는 사람 구분하기
            if (uid.equals(chatroom.getMember().getUid())) {
                chat.setSend(uid);
                chat.setReceive(chatroom.getBoard().getMember().getUid());
            } else {
                chat.setSend(uid);
                chat.setReceive(chatroom.getMember().getUid());
            }

            // 파일을 보낼 경우
            // System.out.println("채팅메세지파일=>" + file);
            if (file != null) {
                if (!file.isEmpty()) {
                    chat.setChimage(file.getBytes());
                    chat.setChimagename(file.getOriginalFilename());
                    chat.setChimagesize(file.getSize());
                    chat.setChimagetype(file.getContentType());
                }
            }

            // 채팅 담기
            chat.setChatroom(chatroom);
            chat.setReview(null);

            // 넣기
            int ret = cService2.insertMessage(chat);
            if (ret == 1) {
                // 채팅방 START_MESSAGE 1로 바꾸기

                chatroom.setStartMessage(1L);
                int ret2 = cService2.updateStartMessage(chatroom);
                if (ret2 == 1) {
                    map.put("status", 200);
                    map.put("result", "잘들어감");
                } else {
                    map.put("status", 2);
                    map.put("result", "db에 들어갔으나 메시지시작은 안바뀜");
                }

            } else {
                map.put("status", 0);
                map.put("result", "잘안들어감");
            }

        } catch (Exception e) {
            map.put("status", -1);
            e.printStackTrace();
        }
        return map;
    }

    // 채팅 목록 불러오기
    @RequestMapping(value = "/messageList", method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> messageList(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(name = "crno") Long crno) {

        Map<String, Object> map = new HashMap<>();
        try {
            if (page == 0) {
                page = 1;
            }
            Pageable pageable = PageRequest.of(0, 15 * page);
            List<ChatEntity> list = cService2.selectChatList(pageable, crno);

            if (list.size() > 0) {
                map.put("result", list);
                map.put("status", 200);
            } else {

                map.put("status", 0);
            }

        } catch (Exception e) {
            map.put("status", -1);
            e.printStackTrace();
        }
        return map;
    }

    // 안읽은 채팅 표시하기
    @RequestMapping(value = "/unReadCount", method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> unReadCount(
            @RequestHeader(name = "token") String token,
            @RequestParam(name = "crno") Long crno) {

        Map<String, Object> map = new HashMap<>();
        try {
            String uid = jwtUtil.extractUsername(token);
            // 1. 토큰을 가지고 crno를 찾는다. 
            List<ChatroomEntity> list = chatroomRepository2.findByMember_uid(uid);
            List<Long> crnoList = new ArrayList();
            for(ChatroomEntity chat :list) {
            	crnoList.add(chat.getCrno());
            }
            
            
            // 2. crno로 unreadcount를 찾는다.
            //crno가 포함된 걸로 바꿔준다. repository에서 수정요망
            Long unReadCount = cService2.selectUnReadCount(uid, crno);
            if (unReadCount != null) {
                map.put("count", unReadCount);
                map.put("status", 200);
            } else {

                map.put("result", "채팅방없음");
                map.put("status", 0);
            }
        }

        catch (Exception e) {
            map.put("status", -1);
            e.printStackTrace();
        }
        return map;
    }

    // 채팅 읽었을 때 안읽은 표시 지우기
    @RequestMapping(value = "/updateCount", method = { RequestMethod.PUT }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> updateCount(
            @RequestHeader(name = "token") String token,
            @RequestParam(name = "crno") Long crno) {
        Map<String, Object> map = new HashMap<>();
        try {
            String uid = jwtUtil.extractUsername(token);
            // 이 경우는 crno를 가져올 수 있음.
            int ret = cService2.updateCount(crno, uid);
            if (ret == 1) {
                map.put("status", 200);
            } else {
                map.put("status", 0);
            }

        } catch (Exception e) {
            map.put("status", -1);
            e.printStackTrace();
        }
        return map;
    }

    // 채팅방 나가기
    @RequestMapping(value = "/exitChatroom", method = { RequestMethod.PUT }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> exitChatroom(
            @RequestHeader(name = "token") String token,
            @RequestParam(name = "crno") Long crno) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            String uid = jwtUtil.extractUsername(token);
            // 현재 로그인 한 사람이 채팅방 나가기
            int ret = cService2.deleteChatRoom(uid, crno);
            if (ret == 1) {
                map.put("status", 200);

            }
        } catch (Exception e) {
            map.put("status", -1);
            e.printStackTrace();
        }
        return map;
    }

    // 상대가 나갔음을 표시하기
    @RequestMapping(value = "/noteExit", method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> noteExit(
            @RequestHeader(name = "token") String token,
            @RequestParam(name = "crno") Long crno) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            String uid = jwtUtil.extractUsername(token);
            int ret = cService2.noteExit(uid, crno);
            if (ret == 1) {
                map.put("status", 200);
                map.put("msg", "상대방이 채팅방을 나갔습니다");

            }
        } catch (Exception e) {
            map.put("status", -1);
            e.printStackTrace();
        }
        return map;
    }

    // 거래예약 버튼 누르기
    @RequestMapping(value = "/makeReservation", method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> makeReservation(
            @RequestParam(name = "crno") Long crno) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            int ret = cService2.makeReservation(crno);
            if (ret == 1) {
                map.put("status", 200);
                map.put("msg", "거래 예약이 되었습니다.");
                // 보드에 인원 수 줄이기!!!!!!!!!!!!!!!!!! 여기 들어감
                // 추가해야함!!!!!!!!!!!!!!!!
            }
        } catch (Exception e) {
            map.put("status", -1);
            e.printStackTrace();
        }
        return map;
    }

    @Autowired
    AlertServiceImpl3 alertServiceImpl3;
    @Autowired
    ChatViewRepository2 chatViewRepository2;

    // 거래완료 버튼 누르기
    // 버튼 누르는 사람 -> 게시판 작성자 또는 사는 사람(채팅 만든 사람)
    // 127.0.0.1:9090/ROOT/api/chat/doneTrade?crno=1
    @RequestMapping(value = "/doneTrade", method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> doneTrade(
            @RequestHeader(name = "token") String token,
            @RequestParam(name = "crno") Long crno) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            int ret = cService2.doneTrade(crno);
            if (ret == 1) {
                map.put("status", 200);
                map.put("msg", "거래가 완료되었습니다.");

                ChatViewEntity chatViewEnt = chatViewRepository2.getById(crno);

                String bodWriter = chatViewEnt.getWriter();
                // 게시판 작성자 등급 확인 후 업 알림 호출
                int ret2 = rankService2.upgradeRank(bodWriter);
                if (ret2 == 1) {
                    map.put("rank", "등급 오름");
                    try {
                        // 알림 DB 저장 호출
                        AlertEntity alert = new AlertEntity();
                        alert.setAltype(5L);
                        // alert.setAlurl(alurl);
                        // 해당 회원 마이페이지 url
                        MemberEntity memEnt = new MemberEntity();
                        memEnt.setUid(bodWriter);
                        alert.setMember(memEnt);

                        alertServiceImpl3.insertAlert(alert);

                        // 등급 업 회원에게 알림 호출
                        alertServiceImpl3.sendRankUpAlert(chatViewEnt, alert);

                    } catch (Exception e) {
                        e.printStackTrace();
                        // System.out.println("판매자등급답변호출에러===>" + e);
                        map.put("status", 100);
                    }
                    // System.out.println(ret2);
                }

                String clickPs = chatViewEnt.getClickperson();
                // 클릭한 사람 등급 확인 후 업 알림 호출
                int ret3 = rankService2.upgradeRank(clickPs);
                if (ret3 == 1) {
                    map.put("rank", "등급 오름");
                    try {
                        // 알림 DB 저장 호출
                        AlertEntity alert = new AlertEntity();
                        alert.setAltype(5L);
                        // alert.setAlurl(alurl);
                        // 해당 회원 마이페이지 url
                        MemberEntity memEnt = new MemberEntity();
                        memEnt.setUid(clickPs);
                        alert.setMember(memEnt);

                        alertServiceImpl3.insertAlert(alert);

                        // 등급 업 회원에게 알림 호출
                        alertServiceImpl3.sendRankUpAlert(chatViewEnt, alert);

                    } catch (Exception e) {
                        e.printStackTrace();
                        // System.out.println("구매자등급답변호출에러===>" + e);
                        map.put("status", 100);
                    }
                    // System.out.println(ret3);
                }
                // 거래 완료 버튼 누른 후 리뷰 작성 여부 확인 알림(10분 후 도착)
                try {
                    // 알림 DB 저장 호출
                    AlertEntity alert = new AlertEntity();
                    alert.setAltype(6L);
                    // 후기 작성 페이지로 이동
                    alert.setAlurl("/ROOT/api/review/insert?crno=" + chatViewEnt.getCrno());
                    MemberEntity memEnt = new MemberEntity();
                    memEnt.setUid(clickPs);
                    alert.setMember(memEnt);

                    alertServiceImpl3.insertAlert(alert);

                    // 구매자에게 후기 작성 여부 알림 호출
                    alertServiceImpl3.sendInsertReviewAlert(chatViewEnt, alert);

                } catch (Exception e) {
                    e.printStackTrace();
                    // System.out.println("후기답변호출에러===>" + e);
                    map.put("status", 100);
                }
            }
        } catch (Exception e) {
            map.put("status", -1);
            e.printStackTrace();
        }
        return map;
    }

}
