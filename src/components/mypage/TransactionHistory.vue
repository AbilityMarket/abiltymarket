<template>
  <WriteReview :reviewOne="state.reviewOne" v-if="state.reviewOne.crno !== undefined"></WriteReview>
  <ViewReview :viewOne="state.viewOne"></ViewReview>
  <div class="main" v-if="state&&state.reviewOne.crno ===undefined&&state.viewOne===undefined">
  <!-- <div class="main" v-if="state"> -->
  
    <h3>내역</h3>
    <p>마켓에서 구매한 상품 및 거래 내용을 관리할 수 있어요</p>
    <header>
      <div class="header_1">
        <div class="header_icon"><ion-icon name="apps-outline"></ion-icon></div>
        <div class="count">
          <p>전체</p>
          <p>{{ state.TW }}건</p>
        </div>
      </div>

      <div class="header_2">
        <div class="header_icon">
          <ion-icon name="warning-outline"></ion-icon>
        </div>
        <div class="count">
          <p>진행중</p>
          <p>{{ state.TN }}건</p>
        </div>
      </div>
      <div class="header_3">
        <div class="header_icon">
          <ion-icon name="hourglass-outline"></ion-icon>
        </div>
        <div class="count">
          <p>예약중</p>
          <p>{{ state.TR }}건</p>
        </div>
      </div>
      <div class="header_4">
        <div class="header_icon">
          <ion-icon name="chevron-down-circle-outline"></ion-icon>
        </div>
        <div class="count">
          <p>종료</p>
          <p>{{ state.TD }}건</p>
        </div>
      </div>
    </header>

    <!-- 내역이 없는 경우 -->
    <section v-if="state.empty">
      <img :src="state.oops" style="width: 40px" />
      <p class="p1">마켓 구매 내역이 없습니다.</p>
      <p class="p2">나에게 맞는 재능을 찾아보세요.</p>
      <v-btn class="btn"
        ><router-link class="btn_a" to="/trade2"
          >마켓보러 가기</router-link
        ></v-btn
      >
    </section>

    <!-- 내역이 있는 경우 -->
    <div class="not_empty" v-if="!state.empty">
      <div class="box" v-for="(tmp, idx) of state.list" :key="tmp">
        <ion-icon style="font-size: 30px" name="checkmark-outline"></ion-icon>
        <h5>{{ state.Tstring[idx] }}</h5>
        <div class="left">
          <div class="img_box">
            <img :src="state.boardImg[idx]" alt="" />
          </div>
        </div>
        <div class="center">
          <div class="center_left">
            <div class="title">
              {{ tmp.btitle }}
            </div>
            <span class="content">{{ tmp.bcontent }}</span>
            <div class="price">{{ tmp.bprice }} 원</div>
          </div>
          <div class="center_right">
            <!-- <h5>일정</h5> -->
            <div class="done">모집중</div>
            <div class="count">모집인원 : {{ tmp.bcount }}</div>
            <div class="enddate">마감일 : {{ tmp.benddate }}</div>
            <div class="address">장소: {{ tmp.baddress }}</div>
          </div>
        </div>
        <div class="right">
          <div class="right_half" v-if="state.Tstring[idx] !== '종료'&&tmp.reviewRevno===null">
            <v-btn class="chat">채팅하기</v-btn>
            <v-btn class="cancel">취소하기</v-btn>
          </div>

          <div class="right_half" v-if="state.Tstring[idx] === '종료'&&tmp.reviewRevno===null">
            <v-btn class="writereview" @click="writeReview(tmp)">리뷰쓰기</v-btn>
            <v-btn class="chat2">채팅하기</v-btn>
          </div>

          <div class="right_half" v-if="tmp.reviewRevno !== null">
            <v-btn class="writereview" @click="viewReview(tmp)">리뷰보기</v-btn>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { reactive } from "@vue/reactivity";
import { onMounted } from "@vue/runtime-core";
import router from "@/routes";
import WriteReview from "./WriteReview.vue";
import ViewReview from "./ViewReview.vue";
export default {
  components:{
    WriteReview, ViewReview
  },
  setup() {
    const state = reactive({
      oops: require("../../assets/images/oops.png"),
      boardImg: [],
      token: sessionStorage.getItem("TOKEN"),
      TW: 0,
      TN: 0,
      TR: 0,
      TD: 0,
      reviewOne: {},
      Tstring: [],
    });

    const handleData = async () => {
      const url = "/ROOT/api/mypage/transactionHistory";
      const headers = {
        "content-type": "application/json",
        token: state.token,
      };
      const response = await axios.get(url, { headers });
      console.log("이력 handleData", response);
      if (response.data.status === 200) {
        state.list = response.data.list;
        state.empty2 = true;
        for (let i = 0; i < state.list.length; i++) {
          state.boardImg.push("/ROOT/api/board/image?bno="+state.list[i].bno)
          // console.log(state.list[i].bno)
          if (state.list[i].chstate === "N") {
            state.TN += 1;
            state.Tstring.push("진행중");
          } else if (state.list[i].chstate === "TDONE") {
            state.TD += 1;
            state.Tstring.push("종료");
          } else if (state.list[i].chstate === "RSV") {
            state.TR += 1;
            state.Tstring.push("예약중");
          }
        }
        state.TW = state.TN + state.TR + state.TD;
      } else if (response.data.status === 0) {
        state.empty = true;
      }
      console.log(state.boardImg)
    };

    // 팝업창 등장
    const writeReview = (reviewOne) => {
      window.scrollTo(0,0);
      state.reviewOne= reviewOne;
      console.log(state.reviewOne.crno)
    };

    const viewReview = (viewOne) => {
      window.scrollTo(0,0);
      state.viewOne= viewOne;
      console.log(state.viewOne.crno)
    };

    onMounted(() => {
      handleData();
    });

    return {
      state,
      writeReview,
      viewReview,
    };
  },
};
</script>

<style scoped src= "../../assets/css/transactionHistory.css">
</style>