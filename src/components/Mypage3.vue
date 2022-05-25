<template>
  <div class="main" v-if="state.profileImg">
    <aside>
      <div class="image">
        <img :src="state.profileImg" />
        <!-- <img :src="state.img2" /> -->
        <!-- <img src="/ROOT/api/member/image?uid=" +state.profileImg /> -->
      </div>
      <div class="uid">
        <span>아이디들어감</span>
      </div>

      <div class="rank">
          <img :src="state.rank" alt="">
      </div>

      <v-expansion-panels class="accordion" multiple variant="accordion">
        <v-expansion-panel class="elevation-0">
          <v-expansion-panel-title>
            <p style="margin-left: 8px">프로필</p>
          </v-expansion-panel-title>
          <v-expansion-panel-text>
            <ul class="profile">
              <li @click="handleList('info')">정보</li>
              <li @click="handleList('interestSet')">관심사 설정</li>
              <li @click="handleList('changePassword')">비밀번호 변경</li>
              <li>알림 설정</li>
            </ul>
          </v-expansion-panel-text>
        </v-expansion-panel>
        <v-expansion-panel>
          <v-expansion-panel-title>
            <p style="margin-left: 8px">마켓</p>
          </v-expansion-panel-title>
          <v-expansion-panel-text>
            <ul class="profile">
              <li @click="handleList('likeList')">찜 목록</li>
              <li @click="handleList('write')">내가 쓴 글</li>
              <li>리뷰</li>
              <li @click="handleList('transactionHistory')">내역</li>
              <li @click="handleList('transactionHistory2')">내역비어있음</li>
            </ul>
          </v-expansion-panel-text>
        </v-expansion-panel>
        <v-expansion-panel>
          <v-expansion-panel-title>
            <p style="margin-left: 8px">설정</p>
          </v-expansion-panel-title>
          <v-expansion-panel-text>
            <ul class="profile">
              <li>차단 목록</li>
              <li @click="handleList('leave')">회원탈퇴</li>
            </ul>
          </v-expansion-panel-text>
        </v-expansion-panel>
      </v-expansion-panels>
    </aside>

    <section>
      <changePassword v-if="state.components === 'changePassword'"></changePassword>
      <leave v-if="state.components === 'leave'"></leave>
      <info v-if="state.components === 'info'"></info>
      <likeList v-if="state.components === 'likeList'"></likeList>
      <write v-if="state.components === 'write'"></write>
      <interestSet v-if="state.components === 'interestSet'"></interestSet>
      <transactionHistory v-if="state.components === 'transactionHistory'"></transactionHistory>
      <transactionHistory2 v-if="state.components === 'transactionHistory2'"></transactionHistory2>
    </section>
  </div>
</template>

<script>
import { reactive } from "@vue/reactivity";
import changePassword from "./mypage/ChangePassword.vue";
import leave from "./mypage/Leave.vue";
import info from "./mypage/Info.vue";
import likeList from "./mypage/LikeList.vue";
import write from "./mypage/Write.vue";
import interestSet from "./mypage/InterestSet.vue";
import transactionHistory from "./mypage/TransactionHistory.vue";
import transactionHistory2 from "./mypage/TransactionHistory2.vue";
import axios from 'axios';
import { onMounted } from "@vue/runtime-core";
export default {
  components: {
    changePassword,
    leave,
    info,
    likeList,
    write,
    interestSet,
    transactionHistory,
    transactionHistory2,
  },
  setup() {
    const state = reactive({
      components: "",
      profileImg: require("../assets/images/파이리.png"),
      // profileImg: require("../assets/images/파이리.png"),
      rank: require("../assets/images/medal1.png"),
    });

    const handleProfileImage = async()=>{
      const url = "/ROOT/api/member/tokenimage";
      const headers = {"content-type": "application/json",
      "token": sessionStorage.getItem("TOKEN")};
      const response = await axios.get(url, {headers});
      console.log(response)
      if(response.data.status ===200){
        // state.profileImg = response.data.uid
      }
    }

    onMounted(() => {});
    const handleList = (no) => {
      state.components = no;
      console.log(state.components);
      console.log(no);
      console.log(typeof no);
      handleProfileImage();
      // hadn();
    };

    // const hadn = async()=>{
    //   const url = `/ROOT/api/member/image?uid=${state.profileImg}`;
    //   const headers= {"content-type": "application/json"};
    //   const response = await axios.get(url, {headers});
    //   state.img2 = response.data;
    //   }


    return {
      state,
      handleList,
    };
  },
};
</script>

<style scoped src="../assets/css/mypage3.css">
</style>