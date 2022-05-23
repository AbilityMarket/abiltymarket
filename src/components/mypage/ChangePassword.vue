<template>
  <div class="main">
    <h3>비밀번호 변경</h3>
    <div class="password">
      <div>기존 비밀번호</div>
      <input ref="pw1" v-model="state.pw1" type="password" />
    </div>

    <div class="new">
      <div class="password">
        <div>새로운 비밀번호</div>
        <input ref="pw2" v-model="state.pw2" type="password" />
      </div>

      <div class="password">
        <div>비밀번호 확인</div>
        <input ref="pw3" v-model="state.pw3" type="password" />
      </div>
    </div>
    <input type="button" value="비밀번호 변경" class="btn" @click="handleClick">
  </div>
</template>

<script>
import axios from "axios";
import { reactive, ref } from "@vue/reactivity";
export default {
  setup() {
    const pw1 = ref(null);
    const pw2 = ref(null);
    const pw3 = ref(null);
    const state = reactive({
      pw1: "",
      pw2: "",
      pw3: "",
    });

    const handleClick = async () => {
      console.log("handleClick");

      if (state.pw1 === "") {
        alert("기존 비밀번호를 입력해주세요");
        pw1.value.focus();
        return false;
      }
      if (state.pw2 === "") {
        alert("새로운 비밀번호를 입력해주세요");
        pw2.value.focus();
        return false;
      }
      if (state.pw3 === "") {
        alert("비밀번호 확인을 입력해주세요");
        pw3.value.focus();
        return false;
      }
      if (state.pw2 !== state.pw3) {
        alert("비밀번호가 다릅니다.");
        pw3.value.focus();
        return false;
      }
      // 프론트 유효성 감사

      const url = "/ROOT/api/member/changePw";
      const headers = {
        "content-type": "multipart/form-data",
        token: sessionStorage.getItem("TOKEN"),
      };
      const body = new FormData();
      body.append("pw1", state.pw1);
      body.append("pw2", state.pw2);
      const response = await axios.post(url, body, { headers });
      console.log(response);
      if (response.data.status === 200) {
        alert("암호 변경 완료");
        sessionStorage.setItem("TOKEN", response.data.token);
      }
      else if(response.data.status === 0){
        alert("기존 비밀번호가 아닙니다.")
      }
      else{
        alert("암호 변경을 실패했습니다.")
      }
    };

    return {
      pw1,
      pw2,
      pw3,
      state,
      handleClick,
    };
  },
};
</script>

<style scoped src="../../assets/css/changepassword2.css"></style>
