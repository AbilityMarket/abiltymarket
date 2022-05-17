<template>
  <div class="main" v-if="state">
    <header>
      <!-- 가운데 프로필 사진 등 -->
      <div class="header_center">
        <div class="center1">
          <div class="img_box">
            <img
              :src="state.imgUrl"
              style="
                width: 100%;
                height: 100%;
                border-radius: 100%;
                object-fit: cover;
              "
            />
            <button @click="handleImageClick"></button>
            <input
              @change="handleChangeImage"
              type="file"
              ref="imageFile"
              style="display: none"
            />
          </div>
          <div class="rank_box">
            <img :src="state.rankImg" style="width: 100%; height: 100%" />
          </div>
          <div class="nickname">
            <span
              style="
                display: inline-block;
                padding-top: 8px;
                padding-bottom: 8px;
              "
              v-if="!state.isPenClicked"
              >닉네임이들어갑니다</span
            >
            <img
              v-if="!state.isPenClicked"
              :src="state.pen"
              class="pen"
              @click="handlePenClick"
            />

            <input
              placeholder="변경할 닉네임을 입력해주세요."
              class="editNickname"
              type="text"
              v-if="state.isPenClicked"
            />
            <button
              class="nickname_btn"
              @click="handleChangeNickname"
              v-if="state.isPenClicked"
            >
              등록
            </button>
          </div>
        </div>
        <div class="center_side">
          <div class="center2">
            <div class="count">0</div>
            <div class="board">게시물</div>
          </div>
          <div class="center3">
            <div class="count">0</div>
            <div class="board">거래 이력</div>
          </div>
          <div class="center4">
            <div class="count">4</div>
            <div class="board">등급</div>
          </div>
        </div>
      </div>
    </header>

    <!--가운데 알림, 좋아요, 차단목록  -->
    <section>
      <div class="alert">
        <ion-icon name="notifications-outline"></ion-icon>
        <span>알람</span>
      </div>
      <div class="like">
        <ion-icon name="heart-outline"></ion-icon>
        <span>좋아요</span>
      </div>
      <div class="block">
        <ion-icon name="eye-off-outline"></ion-icon>
        <span>차단목록</span>
      </div>
    </section>

    <!--  -->
    <footer>
      <div class="footer_left" @click="handleFooterTab">
        <img :src="state.address" />
        동네 설정
        <!--         
        
          <template v-slot:title>현재 설정된 동네</template>
          <v-card-text>asdasdsad</v-card-text>
          <v-card-text>{{ state.addr1 }}{{ state.addr2 }}
            <button>다시 설정하기</button>
            
          </v-card-text>
          
        </v-card>
          
        </div> -->
        <div class="card">
          <v-card
            class="mx-auto footer_left_tab"
            max-width="344"

          >
            <v-card-header style="padding:20px">
              <div>
                <div class="text-overline mb-1">현재 주소</div>
                <div class="text-h6 mb-1">Headline</div>
                <div class="text-caption">
                  {{state.addr1}}{{state.addr2}}
                  <span class="setting" @click="showApi">설정</span>
                </div>
              </div>
            </v-card-header>

            <v-card-actions>
              <v-btn variant="outlined"> Button </v-btn>
            </v-card-actions>
          </v-card>
        </div>
      </div>

      <div class="footer_right">
        <img :src="state.interest" />
        관심사 설정
      </div>
    </footer>
    <div ref="embed"></div>
  </div>
</template>

<script>
import axios from "axios";
import { reactive, ref } from "@vue/reactivity";
export default {
  setup() {
    const imageFile = ref("null");
    const state = reactive({
      pen: require("../../assets/images/pen.png"),
      imgUrl: require("../../assets/images/파이리.png"),
      alert: require("../../assets/images/alert.png"),
      like: require("../../assets/images/like.png"),
      block: require("../../assets/images/block.png"),
      rankImg: require("../../assets/images/medal4.png"),
      address: require("../../assets/images/address.png"),
      interest: require("../../assets/images/interest.png"),
      isPenClicked: false,
      zip: "",
      addr1: "",
      addr2: "",
      footerTab: false,
    });

    const handlePenClick = () => {
      state.isPenClicked = true;
    };

    const handleImageClick = () => {
      imageFile.value.click();
    };
    const handleChangeImage = (e) => {
      console.log("BoardWirte.vue=>handleImg", e);
      console.log(e.target.files[0]);
      if (e.target.files[0]) {
        state.imgData = e.target.files[0];
        state.imgUrl = URL.createObjectURL(e.target.files[0]);
      } else {
        state.imgData = "";
        state.imgUrl = require("../../assets/images/디그다.png");
      }
    };
    const handleChangeNickname = () => {
      state.isPenClicked = false;
    };

    const embed = ref(null);

    const handleFooterTab = () => {
      state.footerTab = true;
    };

    const showApi = async () => {
      new window.daum.Postcode({
        oncomplete: async (data) => {
          let fullRoadAddr = data.roadAddress;
          let extraRoadAddr = "";
          if (data.bname !== "" && /[동|로|가]$/g.test(data.bname)) {
            extraRoadAddr += data.bname;
          }
          if (data.buildingName !== "" && data.apartment === "Y") {
            extraRoadAddr +=
              extraRoadAddr !== ""
                ? ", " + data.buildingName
                : data.buildingName;
          }
          if (extraRoadAddr !== "") {
            extraRoadAddr = " (" + extraRoadAddr + ")";
          }
          if (fullRoadAddr !== "") {
            fullRoadAddr += extraRoadAddr;
          }
          state.zip = data.zonecode; //5자리 새우편번호 사용
          state.addr1 = fullRoadAddr;
          const config = {
            headers: {
              Authorization: "KakaoAK eddc9574385a3fb5f33707a8d3bfcb98",
            },
          };
          const url =
            "https://dapi.kakao.com/v2/local/search/address.json?query=" +
            state.addr1;
          const response = await axios.get(url, config);
          console.log(response);
        },
      }).open();
    };

    return {
      state,
      imageFile,
      embed,
      handlePenClick,
      handleImageClick,
      handleChangeImage,
      handleChangeNickname,
      showApi,
      handleFooterTab,
    };
  },
};
</script>

<style scoped src="../../assets/css/info2.css">
</style>
