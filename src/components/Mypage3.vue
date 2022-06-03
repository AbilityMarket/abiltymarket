<template>
  <div class="main" v-if="state.profileImg">
    <aside>
      <div class="image">
        <img :src="storeUimg" />
      </div>
      <div class="uid">
        <span>{{ storeUnickname }}</span>
      </div>

      <div class="rank">
        <img :src="storeRankimg" alt="" />
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
              <!-- <li>알림 설정</li> -->
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
              <!-- <li @click="handleList('review')">리뷰</li> -->
              <li @click="handleList('transactionHistory')">내역</li>
              <!-- <li @click="handleList('transactionHistory2')">내역비어있음</li> -->
            </ul>
          </v-expansion-panel-text>
        </v-expansion-panel>
        <v-expansion-panel>
          <v-expansion-panel-title>
            <p style="margin-left: 8px">설정</p>
          </v-expansion-panel-title>
          <v-expansion-panel-text>
            <ul class="profile">
              <!-- <li>차단 목록</li> -->
              <li @click="handleList('leave')">회원탈퇴</li>
            </ul>
          </v-expansion-panel-text>
        </v-expansion-panel>
      </v-expansion-panels>
    </aside>

    <section>
      <changePassword
        v-if="state.components === 'changePassword'"
      ></changePassword>
      <leave v-if="state.components === 'leave'"></leave>
      <info v-if="state.components === 'info'"></info>
      <likeList v-if="state.components === 'likeList'"></likeList>
      <write v-if="state.components === 'write'"></write>
      <review v-if="state.components === 'review'"></review>
      <interestSet v-if="state.components === 'interestSet'"></interestSet>
      <transactionHistory
        v-if="state.components === 'transactionHistory'"
      ></transactionHistory>
      <transactionHistory2
        v-if="state.components === 'transactionHistory2'"
      ></transactionHistory2>
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
import review from "./mypage/Review.vue";
import interestSet from "./mypage/InterestSet.vue";
import transactionHistory from "./mypage/TransactionHistory.vue";
import transactionHistory2 from "./mypage/TransactionHistory2.vue";
import axios from "axios";
import { onMounted, computed } from "@vue/runtime-core";
import { useRoute } from "vue-router";
import { useRouter } from "vue-router";
import { useStore } from "vuex";
export default {
  components: {
    changePassword,
    leave,
    info,
    review,
    likeList,
    write,
    interestSet,
    transactionHistory,
    transactionHistory2,
  },
  setup() {
    const route = useRoute();
    const router = useRouter();
    const store = useStore();

    const state = reactive({
      components: "info",
      profileImg: require("../assets/images/파이리.png"),
      // profileImg: require("../assets/images/파이리.png"),
      rank: require("../assets/images/medal1.png"),
    });

    const handleProfileImage = async () => {
      store.dispatch('handleMember');

       const url ="/ROOT/api/member/selectmember"
            const headers = {"content-type":"application/json",
        "token": sessionStorage.getItem("TOKEN")};
            const response = await axios.get(url,{headers});
            console.log(response);
            if(response.data.status ===200){
                store.commit("setUnickname", response.data.unickname)
                store.commit("setUid", response.data.uid)
                store.commit("setUimg", response.data.uid)
                store.commit("setRankimg", response.data.uid)
                // console.log("store > handelMemberactio>동작!");
            }
    };

    onMounted(() => {
      
      if (typeof route.query.page === "undefined") {
        handleList("info");
      } else {
        handleList(route.query.page);
      }
      handleProfileImage();
    });

    let storePage = computed(() => {
      return store.getters.getPage;
    });

    let storeUimg = computed(() => {
      console.log("storeUimg동작!")
      return store.getters.getUimg;
    });

    let storeUnickname = computed(() => {
      console.log("storeUimg동작!")
      return store.getters.getUnickname;
    });

    let storeRankimg = computed(() => {
      return store.getters.getRankimg;
    });

    // 컴포넌트 이동
    const handleList = (no) => {
      store.commit("setPage", no);
      state.components = storePage;
      router.push({ name: "Mypage", query: { page: state.components } });
      console.log(state.components);
      console.log(no);
      console.log(typeof no);
    };

    return {
      state,
      handleList,
      storePage,
      storeUnickname,
      storeUimg,
      storeRankimg
    };
  },
};
</script>

<style scoped src="../assets/css/mypage3.css">
</style>