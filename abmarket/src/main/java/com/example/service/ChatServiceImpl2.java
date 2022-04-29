package com.example.service;

import java.util.ArrayList;
import java.util.List;

import com.example.entity.BoardEntity;
import com.example.entity.ChatEntity;
import com.example.entity.ChatroomEntity;
import com.example.entity.ChatViewEntity;
import com.example.entity.MemberEntity;
import com.example.repository.BoardRepository2;
import com.example.repository.ChatRepository2;
import com.example.repository.ChatroomRepository2;
import com.example.repository.ChatViewRepository2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl2 implements ChatService2 {

    @Autowired
    ChatRepository2 cRepository2;

    @Autowired
    ChatroomRepository2 chatroomRepository2;

    @Autowired
    ChatViewRepository2 chatViewRepository2;

    @Autowired
    BoardRepository2 bRepository2;

    // 채팅방이 있나 확인하기
    @Override
    public ChatroomEntity searchChatRoom(String uid, Long bno) {
        try {
            BoardEntity board = bRepository2.findById(bno).orElse(null);
            // System.out.println(board);
            if (board != null) {
                MemberEntity member = board.getMember();
                String boardWriterid = member.getUid();
                ChatroomEntity chatroom = chatroomRepository2.findByMember_uidAndBoard_member_uid(uid, boardWriterid);
                System.out.println(uid);
                System.out.println(boardWriterid);
                return chatroom;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 채팅방 만들기
    @Override
    public int createChatRoom(String uid, Long bno) {
        try {
            // 멤버
            MemberEntity member = new MemberEntity();
            member.setUid(uid);

            BoardEntity board = bRepository2.findById(bno).orElse(null);
            if (board != null) {
                System.out.println("토큰에서가져온멤버" + uid);
                System.out.println("게시판에거사져온멤버" + board.getMember().getUid());
                // 본인글일 경우
                if (board.getMember().getUid().equals(uid)) {
                    System.out.println("본인글");
                    return 3;
                }
                // 본인 글 아닐경우
                else {
                    System.out.println("여기2");
                    ChatroomEntity chatroom = new ChatroomEntity();
                    chatroom.setBoard(board);
                    chatroom.setMember(member);
                    ChatroomEntity retChatroom = chatroomRepository2.save(chatroom);
                    // 저장이 정상적으로 된다면
                    if (retChatroom != null) {
                        System.out.println("정상작동 저장");
                        return 1;
                    }
                    // 저장이 정상적으로 안됐다면
                    else {
                        System.out.println("정상작동 저장실패");
                        return 2;
                    }
                }

                // 보드가 없는 경우
            } else {
                System.out.println("보드가 없으");
                return 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }

    }

    // 채팅목록
    @Override
    public List<ChatEntity> selectChatList(Long crno) {
        try {
            // 채팅번호로 채팅목록 가져오기
            return cRepository2.findByChatroom_crno(crno);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    // 채팅입력
    @Override
    public int insertMessage(ChatEntity chat) {

        try {
            cRepository2.save(chat);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }

    // 채팅방 목록 가져오기 (내가 보낸 거 + 받은 거)
    @Override
    public List<ChatViewEntity> selectChatRoomList(String uid) {
        try {

            // 내 아이디가 보낸 사람, 받는 사람이고
            // 채팅을 시작한 채팅방 조회
            List<ChatViewEntity> list = chatViewRepository2
                    .findChatroom(uid,
                            uid, 1L);

            // 조회한 채팅방 중에 내가 나간 채팅방 필터링하기
            List<ChatViewEntity> list2 = new ArrayList<>();
            for (ChatViewEntity chatview : list) {
                if ((!chatview.getChstate().equals(uid)) &&
                        (!chatview.getChstate().equals("DONE"))) {
                    list2.add(chatview);
                }
            }
            System.out.println(list2);
            return list2;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 채팅방나가기
    @Override
    public int deleteChatRoom(String uid, Long crno) {
        try {
            List<ChatEntity> chats = cRepository2.findByChatroom_crno(crno);
            for (ChatEntity chat : chats) {

                // 두번째 사람이 나갈경우
                if (chat.getChstate().equals(chat.getSend()) ||
                        chat.getChstate().equals(chat.getReceive())) {
                    chat.setChstate("DONE");
                    cRepository2.save(chat);
                } else {
                    // 처음 한 사람이 나갈경우
                    chat.setChstate(uid);
                    cRepository2.save(chat);
                }

            }
            if (chats.size() > 0) {
                return 1;
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }

    }

    // 안읽은 채팅 표시하기
    @Override
    public Long selectUnReadCount(String uid, Long crno) {
        try {
            Long count = 1L;
            Long unReadCount = cRepository2.countByReceiveAndUnReadCountAndChatroom_crno(uid, count, crno);
            return unReadCount;
        }

        catch (Exception e) {
            e.getStackTrace();
            return null;
        }

    }

    // 채팅 읽었을 때 안읽은 표시 지우기
    @Override
    public int updateCount(Long crno, String uid) {
        try {
            List<ChatEntity> list = cRepository2.findByUnReadCountAndReceiveAndChatroom_crno(1L, uid, crno);

            for (ChatEntity chat : list) {
                chat.setUnReadCount(0L);
                cRepository2.save(chat);
            }
            if (list.size() > 0) {
                return 1;
            }
            return 0;
        }

        catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    // 채팅보내면 채팅목록에 나올 수 있게 startmessage상태 1로 바꾸기
    @Override
    public int updateStartMessage(ChatroomEntity chatroom) {
        try {
            chatroomRepository2.save(chatroom);
            return 1;
        } catch (Exception e) {
            e.getStackTrace();
            return 0;
        }
    }

    // 상대가 나갔다고 표시하기
    @Override
    public int noteExit(String uid, Long crno) {
        try {
            ChatViewEntity chatview = chatViewRepository2.findById(crno).orElse(null);
            if (chatview != null) {
                if (chatview.getChstate().equals(chatview.getWriter())
                        || chatview.getChstate().equals(chatview.getClickperson())) {
                    return 1;
                }
            }

            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }

    }

    // 채팅 나눈 개수 표시하기(게시판에 하트, 채팅 수 확인용)
    @Override
    public Long chatcount(Long bno) {
        try {
            // 게시판 번호로 챗뷰 리스트 찾기
            List<ChatViewEntity> chatroomList = chatViewRepository2.findByBoardBno(bno);

            // 각 채팅번호로 각 채팅개수 찾고 더하기
            Long count = 0L;
            for (ChatViewEntity chatroom : chatroomList) {
                Long count2 = cRepository2.countByChatroom_crno(chatroom.getCrno());
                count += count2;
            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public int makeReservation(Long crno) {
        try {
            // 채팅번호에 일치하는 채팅목록 찾기
            List<ChatEntity> chatlist = cRepository2.findByChatroom_crno(crno);

            // 채팅테이블에 채팅상태 컬럼 RESERVE상태로 바꾸기
            for (ChatEntity chat : chatlist) {
                chat.setChstate("RSV");
                cRepository2.save(chat);
            }

            // 정상작동 시 1리턴
            if (chatlist != null) {
                return 1;
            }

            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

}
