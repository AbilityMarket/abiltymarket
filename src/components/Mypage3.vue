<template>
  <div class="main" v-if="state.profileImg">
    <aside>
      <div class="image">
        <img :src="state.profileImg" />
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
              <li>관심사 설정</li>
              <li @click="handleList(1)">비밀번호 변경</li>
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
              <li>찜 목록</li>
              <li>내가 쓴 글</li>
              <li>리뷰</li>
              <li>내역</li>
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
              <li @click="handleList(2)">회원탈퇴</li>
            </ul>
          </v-expansion-panel-text>
        </v-expansion-panel>
      </v-expansion-panels>
    </aside>

    <section>
      <menu-1 v-if="state.components === 1"></menu-1>
      <menu-2 v-if="state.components === 2"></menu-2>
      <info v-if="state.components === 'info'"></info>
    </section>
  </div>
</template>

<script>
import { reactive } from "@vue/reactivity";
import menu1 from "./mypage/Menu1.vue";
import menu2 from "./mypage/Menu2.vue";
import info from "./mypage/Menu3.vue";
import { onMounted } from "@vue/runtime-core";
export default {
  components: {
    menu1,
    menu2,
    info,
  },
  setup() {
    const state = reactive({
      components: "",
      profileImg: require("../assets/images/파이리.png"),
      rank: require("../assets/images/medal1.png"),

    });

    onMounted(() => {});
    const handleList = (no) => {
      state.components = no;
      console.log(state.components);
      console.log(no);
      console.log(typeof no);
    };

    return {
      state,
      handleList,
    };
  },
};
</script>

<style scoped src="../assets/css/mypage3.css">
</style>