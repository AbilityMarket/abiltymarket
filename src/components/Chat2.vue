<template>
  <div>
    <div class="container" v-if="state.list">
      <!-- 헤더 -->
      <header>
        <div class="header_left">
          <span class="left_nickname">로그인 닉네임들어갑니다</span>
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
            <div class="list_chatmessage">
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
{{state.img}}
        <!-- 채팅부분 -->
        <section>
          <div class="chat_message">
            <span
              class="chatOne_box"
              :class="{ right: tmp.send === state.uid }"
              v-for="tmp of state.messageList"
              :key="tmp"
			  
            >
              <div class="chatOne" v-if="tmp.chcontent !==null">{{ tmp.chcontent }}</div>
              <div class="chatOne" v-if="tmp.chcontent ===null">
				  <img :src="state.img"  style="width:100px; height:100px;"/>
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
  </div>
</template>

<script>
import axios from "axios";
import { reactive, ref } from "@vue/reactivity";
import { onMounted } from "@vue/runtime-core";
export default {
  setup() {
    const state = reactive({
      latestChat: [],
      uid: sessionStorage.getItem("UID"),
      conversationPartner: [],
	  img :[],
    });
    const imageFile = ref(null);
    const handleImageAction = () => {
      imageFile.value.click();
    };

    // 이미지 선택할 때
    const handleImage = async(e) => {
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
		const response = await axios.post(url, body, {headers});
		console.log("handleImage=>");
		console.log(response);
		if(response.data.status ===200){
        	clickChatRoom(state.currentCrno);
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
        for (let i = 0; i < state.list.length; i++) {
			if(state.list[i].chcontent ===null){
				state.img[i] = `/ROOT/api/chat/selectlist?chno=${state.list[i].chno}`
			}




          if (state.uid !== state.list[i].clickperson) {
            state.conversationPartner.push(state.list[i].clickperson);
          } else {
            state.conversationPartner.push(state.list[i].writer);
          }
          console.log(state.list[i].crno);
          const url2 = `/ROOT/api/chat/findLastChat?crno=${state.list[i].crno}`;
          const response2 = await axios.get(url2, { headers });
          console.log(response2);
          if (response2.data.status === 200) {
            state.latestChat.push(response2.data.result);
          }
        }
		// console.log(state.img)
      }
    };

    // 채팅방 클릭했을 때 나타나기
    const clickChatRoom = async (crno) => {
      const url = `/ROOT/api/chat/messageList?crno=${crno}`;
      const headers = { "content-type": "application/json" };
      const response = await axios.get(url, { headers });
      console.log("clickChatRoom");
      console.log(response);
      if (response.data.status === 200) {
        state.messageList = response.data.result;
        state.currentCrno = crno;
      }
    };

    // 채팅 보내기
    const sendMessage = async (file) => {
      if (state.content === "" || file === null) {
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
      if (response.data.status) {
        clickChatRoom(state.currentCrno);
        state.content = "";
      }
    };

    onMounted(() => {
      importChatRoomList();
    });

    return {
      importChatRoomList,
      handleImageAction,
      handleImage,
      imageFile,
      state,
      clickChatRoom,
      sendMessage,
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
  flex-direction: column;
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
</style>
