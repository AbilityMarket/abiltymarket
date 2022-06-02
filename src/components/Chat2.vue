<template>
  <div>
    <div class="container" v-if="state.list">
      <!-- 헤더 -->
      <header>
        <!-- {{state.list}} -->
        <div class="header_left">
          <span class="left_nickname">채팅</span>
        </div>
        <div class="header_right">
          <div class="header_imagebox">
            <v-img
              class="header_image"
              src="../assets/images/파이리.png"
              alt=""
            />
          </div>
          <span class="right_nickname">상대방 닉네임</span>
        </div>
      </header>

      <!-- 메인 -->
      <div class="main">
        <!-- 채팅방리스트 -->
        <aside>
          <div
            class="list_main"
            v-for="(tmp, idx) in state.list"
            :key="tmp"
            @click="clickChatRoom(tmp.crno)"
          >
            <div class="imagebox">
              <v-img class="image" src="../assets/images/디그다.png" alt="" />
            </div>
            <div class="list_chatmessage" ref="chatmessage">
              <div class="list_chatmessage_nickname">
                <span>{{ state.conversationPartner[idx] }}</span>
              </div>

              <div class="list_chatmessage_message">
                <span style="overflow: hidden">{{
                  state.latestChat[idx]
                }}</span>
              </div>
            </div>
          </div>
        </aside>
        <!-- {{ state.crnoList }} -->
        <!-- {{ state.img }} -->
        <!-- 채팅부분 -->
        <section>
          <div class="chat_message" ref="chatBox" @scroll="scrollEvent">
            <span
              class="chatOne_box"
              :class="{ right: tmp.send === state.uid }"
              v-for="tmp of state.messageList"
              :key="tmp"
            >
              <div class="chatOne" v-if="tmp.chcontent !== null">
                {{ tmp.chcontent }}
              </div>
              <div class="chatOne" v-if="tmp.chcontent === null">
                <img
                  :src="state.img[tmp.chno]"
                  style="width: 100px; height: 100px"
                />
              </div>
            </span>
          </div>
          <div class="input_message">
            <div class="add_imagebox">
              <v-img class="imagebox_image" src="../assets/images/add.png" />
            </div>
            <input
              type="text"
              v-model="state.content"
              @keypress.enter="sendMessage"
              placeholder="메시지 입력..."
            />
            <div class="picture_imagebox" @click="handleImageAction()">
              <v-img
                class="imagebox_image"
                src="../assets/images/picture.png"
              />
              <input type="file" ref="imageFile" hidden @change="handleImage" />
            </div>
          </div>
        </section>
      </div>
    </div>

    <div class="noChat" v-if="!state.list">
      <img :src="state.oops" style="width: 40px" />
      <p class="p1">마켓 구매 내역이 없습니다.</p>
      <p class="p2">나에게 맞는 재능을 찾아보세요.</p>
      <v-btn class="btn">
        <router-link class="btn_a" to="/trade2">마켓보러 가기</router-link>
      </v-btn>
    </div>

  </div>
</template>

<script>
import mqtt from "precompiled-mqtt";
import axios from "axios";
import { reactive, ref } from "@vue/reactivity";
import { onMounted, renderSlot } from "@vue/runtime-core";
export default {
  setup() {
    const state = reactive({
      oops : require("../assets/images/oops.png"),
      latestChat: [],
      uid: sessionStorage.getItem("UID"),
      conversationPartner: [],
      img: [],
      crnoList: [],
      pageNo: 1,

      message: "", // 보낼 메세지
      client: "", // 접속한 클라이언트 객체

      host: "1.234.5.158", //서버주소
      port: 11884, // web용 포트번호

      options: {
        clean: true, //세션 초기화
        reconnectPeriod: 20000, // 주기적인 접속 시간

        // 고유값 ex)d200, d212 등
        clientId: "d202_" + new Date().getTime(),
        username: "ds606", // 아이디
        password: "ds606", // 암호
      },

      topic: `ds/abilitymarket/${sessionStorage.getItem("UID")}/#`,
      qos: 0, // 0부터 2까지의 숫자
    });
    const imageFile = ref(null);
    const handleImageAction = () => {
      imageFile.value.click();
    };
    const chatmessage = ref(null);
    const chatBox = ref(null);

    const scrollEvent = (e) => {
      console.log(e.target.scrollTop);
      console.log("chatbox.value>");
      console.log(-(chatBox.value.scrollHeight - chatBox.value.clientHeight));
      // const e.target.se
      if (
        e.target.scrollTop >=
          -(chatBox.value.scrollHeight - chatBox.value.clientHeight) &&
        e.target.scrollTop <=
          -(chatBox.value.scrollHeight - chatBox.value.clientHeight) + 5
      ) {
        console.log("-150이다");
        clickChatRoom(state.currentCrno);
        console.log(state.pageNo);
      }
    };

    // 안 읽은 채팅 수
    const unReadCount = async (no) => {
      const url = `/unReadCount${no}`;
      const headers = { "content-type": "application/json" };
      const response = await axios.get(url, { headers });
    };

    // 이미지 선택할 때
    const handleImage = async (e) => {
      console.log("chat2.vue=>handleImage", e);
      if (e.target.files[0]) {
        // state.imgData = e.target.files[0];
        const url = `/ROOT/api/chat/sendMessage?crno=${state.currentCrno}`;
        const headers = {
          "content-type": "multipart/form-data",
          token: sessionStorage.getItem("TOKEN"),
        };
        const body = new FormData();
        body.append("file", e.target.files[0]);
        const response = await axios.post(url, body, { headers });
        console.log("handleImage=>");
        console.log(response);
        if (response.data.status === 200) {
          sendMessage2();
          clickChatRoom(state.currentCrno);
          importChatRoomList();
        }
      }
    };

    // 채팅방 리스트, 최근 채팅 화면에 나타내기
    const importChatRoomList = async () => {
      const url = `/ROOT/api/chat/selectlist`;
      const headers = {
        "content-type": "application/json",
        token: sessionStorage.getItem("TOKEN"),
      };
      const response = await axios.get(url, { headers });
      console.log("chat > importChatRoomList");
      console.log(response);
      if (response.data.status === 200) {
        state.list = response.data.result;
        state.conversationPartner= []
        for (let i = 0; i < state.list.length; i++) {
          if (state.uid !== state.list[i].clickperson) {
            state.conversationPartner.push(state.list[i].clickperson);
          } else {
            state.conversationPartner.push(state.list[i].writer);
          }
          latestChat(state.list[i].crno);
        }
      }
      else if(response.data.status === 0) {
        state.list = false;
      }
    };

    const latestChat = async(no) => {
      const url2 = `/ROOT/api/chat/findLastChat?crno=${no}`;
      const headers = {
        "content-type": "application/json",
        token: sessionStorage.getItem("TOKEN"),
      };
      const response2 = await axios.get(url2, { headers });
      console.log(response2);
      if (response2.data.status === 200) {
        state.latestChat.push(response2.data.result);
      }
    };

    // 채팅방 클릭했을 때 나타나기
    const clickChatRoom = async (crno) => {
      if (state.currentCrno && !crno) {
        const url = `/ROOT/api/chat/messageList?crno=${state.currentCrno}&page=${state.pageNo}`;
        const headers = { "content-type": "application/json" };
        const response = await axios.get(url, { headers });
        console.log("clickChatRoom");
        console.log(response);

        if (response.data.status === 200) {
          state.pageNo++;
          state.messageList = response.data.result;
          state.currentCrno = crno;
          if (response.data.result[0].send === state.uid) {
            state.currentPartner = response.data.result[0].receive;
          } else {
            state.currentPartner = response.data.result[0].send;
          }
          console.log("대화상대" + state.currentPartner);

          state.img.fill(
            false,
            0,
            state.messageList[state.messageList.length - 1].chno
          );
          for (let i = 0; i < state.messageList.length; i++) {
            if (state.messageList[i].chcontent === null) {
              state.img[
                state.messageList[i].chno
              ] = `/ROOT/api/chat/image?chno=${state.messageList[i].chno}`;
            }
          }
          console.log("state.img!!!!!!!!!!!!!!!!" + state.img);
        }
      } else {
        const url = `/ROOT/api/chat/messageList?crno=${crno}&page=${state.pageNo}`;
        const headers = { "content-type": "application/json" };
        const response = await axios.get(url, { headers });
        console.log("clickChatRoom");
        console.log(response);

        if (response.data.status === 200) {
          state.messageList = response.data.result;
          state.currentCrno = crno;
          state.pageNo++;
          if (response.data.result[0].send === state.uid) {
            state.currentPartner = response.data.result[0].receive;
          } else {
            state.currentPartner = response.data.result[0].send;
          }
          console.log("대화상대" + state.currentPartner);

          state.img.fill(
            false,
            0,
            state.messageList[state.messageList.length - 1].chno
          );
          for (let i = 0; i < state.messageList.length; i++) {
            if (state.messageList[i].chcontent === null) {
              state.img[
                state.messageList[i].chno
              ] = `/ROOT/api/chat/image?chno=${state.messageList[i].chno}`;
            }
          }
          console.log("state.img!!!!!!!!!!!!!!!!" + state.img);
        }
      }
    };

    // 채팅 보내기
    const sendMessage = async () => {
      if (state.content === "") {
        alert("메세지를 입력해주세요");
        return;
      }
      const url = `/ROOT/api/chat/sendMessage?crno=${state.currentCrno}`;
      const headers = {
        "content-type": "multipart/form-data",
        token: sessionStorage.getItem("TOKEN"),
      };
      const body = new FormData();

      // 채팅을 입력할 경우
      body.append("chcontent", state.content);
      console.log(state.content);

      const response = await axios.post(url, body, { headers });
      if (response.data.status === 200) {
        sendMessage2();
        clickChatRoom(state.currentCrno);
        importChatRoomList();
        state.content = "";
      }
    };

    const createConnection = () => {
      const url = `ws://${state.host}:${state.port}`;
      try {
        state.client = mqtt.connect(url, state.options);
        console.log(state.client);

        state.client.on("connect", () => {
          console.log("connect success!!");
          console.log("chat2vue=>현재구독중인" + state.topic);
        });

        state.client.on("error", () => {
          console.log("connect error!!");
        });

        state.client.on("message", (topic, message) => {
          console.log("메세지옴을 확인" + topic, JSON.parse(message));
          clickChatRoom(state.currentCrno);
          importChatRoomList();
        });
      } catch (e) {
        console.log("mqtt error", e);
      }
      doSubscribe();
    };

    const doSubscribe = () => {
      state.client.subscribe(state.topic, { qos: state.qos }, (error, res) => {
        if (error) {
          console.log("subscribe topic error", error);
          return;
        }
        console.log("subscribe success", res);
      });
    };

    // 메세지 보내기
    const sendMessage2 = () => {
      // json object => string : JSON.stringify()
      // string => json object : JSON.parse()

      const payload = JSON.stringify({
        userid: state.uid,
        msg: `${state.uid}가 보냄`,
      });
      console.log("sendmessage2보낸다잉" + state.currentPartner);
      console.log("보내는 토픽" + `ds/abilitymarket/${state.currentPartner}`);
      // 보낼 토픽, 보내는내용(문자), qos(0~2)
      state.client.publish(
        `ds/abilitymarket/${state.currentPartner}`,
        payload,
        0,
        (error) => {
          if (error) {
            console.log("publish error", error);
          }
        }
      );
    };

    onMounted(() => {
      importChatRoomList();
      createConnection();
    });

    return {
      chatBox,
      chatmessage,
      importChatRoomList,
      handleImageAction,
      handleImage,
      imageFile,
      state,
      clickChatRoom,
      sendMessage,
      createConnection,
      sendMessage2,
      scrollEvent,
    };
  },
};
</script>

<style lang="css" scoped>
/* 컨테이너 */
.container {
  display: flex;
  width: 95%;
  height: 80vh;
  border: 1px solid black;
  flex-direction: column;
  margin: 0 auto;
}

/* 헤더 */
header {
  border-bottom: 1px solid black;
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
}

/* 헤더 왼쪽 부분 */
header > .header_left {
  width: 30%;
  height: 100%;
  border-right: 1px solid black;
  display: flex;
  align-items: center;
  justify-content: center;
}
header > .header_left > .left_nickname {
  margin: 20px;
}

/* 헤더 오른쪽 부분 */

header > .header_right {
  width: 70%;
  display: flex;
  align-items: center;
  /* justify-content: center; */
}
/* 헤더 오른쪽 부분  이미지*/
header > .header_right > .header_imagebox {
  border: 1px solid rgb(190, 190, 190);
  border-radius: 30px;
  overflow: hidden;
  margin-right: 10px;
  margin-left: 30px;
}

header > .header_right > .header_imagebox > .header_image {
  object-fit: cover;
  width: 30px;
  height: 30px;
}

/* 헤더 아래쪽 */

.main {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100%;
  width: 100%;
  overflow: auto;
}

/* 헤더 아래쪽 왼쪽 */

.main > aside {
  width: 30%;
  height: 100%;
  border-right: 1px solid black;
  overflow: auto;
}

.main > aside > .list_main {
  width: 100%;
  height: 10%;
  /* border-bottom: 1px solid black; */
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
}

aside > .list_main:hover {
  background-color: rgb(240, 240, 240);
}
aside > .list_main > .imagebox {
  border: 1px solid rgb(190, 190, 190);

  border-radius: 30px;
  overflow: hidden;
}
aside > .list_main > .imagebox > .image {
  object-fit: cover;
  width: 50px;
  height: 50px;
}

/* 개별 리스트 */
aside > .list_main > .list_chatmessage {
  width: 65%;
  height: 90%;
  padding: 10px;
  margin-left: 10px;
  overflow: hidden;
  display: flex;
  flex-direction: column;
}

/* 닉네임 */
aside > .list_main > .list_chatmessage > .list_chatmessage_nickname {
  /* height: 20%; */
  font-size: 13px;
  /* margin-bottom: 10px; */
}
/* 메세지 */
aside > .list_main > .list_chatmessage > .list_chatmessage_message {
  color: rgb(141, 141, 141);
  font-size: 13px;
}

/* 채팅부분 */
section {
  width: 70%;
  height: 100%;
  padding: 10px;
  position: relative;
  /* overflow: auto; */
}

section > .chat_message {
  height: 90%;
  display: flex;
  flex-direction: column-reverse;
  overflow: auto;
}

.chat_message > .chatOne_box {
  margin-top: 10px;
  margin-left: 10px;
  margin-right: 10px;

  /* background-color: rgb(212, 212, 212); */
  border-radius: 20px;
  /* background-color: ;
	border: 1px solid black; */
}
.chatOne_box > .chatOne {
  font-size: 15px;
  padding: 10px;
  padding-top: 13px;
  background-color: rgb(212, 212, 212);
  border-radius: 20px;
  display: inline-block;
}

.chatOne_box.right {
  text-align: right;
}

section > .input_message {
  display: flex;
  align-items: center;
  justify-content: space-evenly;
  width: 90%;
  height: 5%;
  margin: 0 auto;
  margin-top: 8px;
  border: 1px solid rgb(206, 206, 206);
  border-radius: 30px;
  padding: 18px 0;
}

section > .input_message > .add_imagebox {
  height: 38px;
  width: 38px;
  object-fit: cover;
  overflow: hidden;
  padding: 4px;
  cursor: pointer;
}

section > .input_message > input {
  height: 100%;
  width: 80%;
  padding: 18px 15px 18px 15px;
  overflow: auto;
  font-size: 13px;
  vertical-align: middle;
}

section > .input_message > input:focus {
  outline: none;
}

section > .input_message > .picture_imagebox {
  height: 38px;
  width: 38px;
  object-fit: cover;
  overflow: hidden;
  padding: 4px;
  cursor: pointer;
}

section > .input_message > .add_imagebox > .imagebox_image,
section > .input_message > .picture_imagebox > .imagebox_image {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.noChat {
  margin-top: 200px;
  text-align: center;
  vertical-align: middle;
}

</style>
