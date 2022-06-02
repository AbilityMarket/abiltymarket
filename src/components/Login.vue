<template>
  <div class="logincontainer">
    <h2>로그인</h2>

    <div class="loginbox">
      <div class="login_form">
        <div class="textbox">
          <input type="text" ref="uid" v-model="state.uid" required />
          <span></span>
          <label>아이디</label>
          <!-- <ion-icon name="checkmark-outline" class="ok"></ion-icon>
          <ion-icon name="close-outline" class="no"></ion-icon> -->
        </div>

        <div class="textbox">
          <input type="password" ref="upw" v-model="state.upw" required />
          <span></span>
          <label>비밀번호</label>
          <!-- <ion-icon name="checkmark-outline" class="ok"></ion-icon>
          <ion-icon name="close-outline" class="no"></ion-icon> -->
        </div>

        <button class="btn_login" @click="handleLogin">로그인</button>
      </div>
    </div>

    <div class="check">
      <div class="idsave">
        <input type="checkbox" /><span>아이디 저장</span>
      </div>

      <div class="checklink" style="margin-left: 110px">
        <a href="#" class="findmail" style="margin-right: 10px"
          ><span>아이디찾기</span></a
        >
        <a href="#" class="findpw"><span>비밀번호찾기</span></a>
      </div>
    </div>

    <!-- 간편 로그인 -->
    <div class="simplelogin" style="margin-top: 30px">
      <dt class="m_hide"><span>SNS 계정으로 간편 로그인</span></dt>

      <div class="sns">
        <div class="snsitem">
          <button class="kakao" @click="kakaoLogin()">
            <v-img
              src="../assets/images/kakao.png"
              style="width: 35px; margin-left: 7px"
            />
          </button>
        </div>
        <div class="snsitem">
          <button class="google" @click="gooleLogin()">
            <v-img
              src="../assets/images/google.png"
              style="width: 37px; margin-left: 6px"
            />
          </button>
        </div>
        <div class="snsitem">
          <button class="naver" @click="naverLogin()">
            <v-img
              src="../assets/images/naver.png"
              style="width: 37px; margin-left: 6px"
            />
          </button>
        </div>
      </div>
    </div>

    <router-link to="/join">
      <div class="join">
        <button class="btn_join">
          당신의 가치를 경험해보세요.
          <span style="text-decoration: underline">회원가입</span>
        </button>
      </div>
    </router-link>
  </div>
</template>

<script>
import { useRouter } from "vue-router";
import axios from "axios";
import { reactive } from "@vue/reactivity";
import { useStore } from "vuex";
export default {
  setup(props, { emit }) {
    const store = useStore();
    const router = useRouter();

    const state = reactive({
      uid: "",
      upw: "",
    });

    const handleLogin = async () => {
      const url = `/ROOT/api/member/login`;
      const headers = { "Content-Type": "form-data" };
      const body = new FormData();

      body.append("uid", state.uid);
      body.append("upw", state.upw);

      const response = await axios.post(url, body, { headers });
      console.log(response.data);
      if (response.data.status === 200) {
        sessionStorage.setItem("TOKEN", response.data.token);
        // store.commit('setLogged', true); // ('메소드명', 변경할 값)
        store.commit("setLogged", true);
        store.commit("setClicklogged", false);
        sessionStorage.setItem("LOGGED", false);
        sessionStorage.setItem("UID", state.uid);
        // emit('createConnection', state.uid);
        router.push({ name: "Home" });
      }
    };
    
    const kakaoLogin = function () {
      window.Kakao.init('0eb4283842adc8eae97cfa6e89a19036');
      if (window.Kakao.Auth.getAccessToken()) {
      window.Kakao.API.request({
        url: '/v1/user/unlink',
        success: function (response) {
          console.log(response)
        },
        fail: function (error) {
          console.log(error)
        },
      })
      window.Kakao.Auth.setAccessToken(undefined)
      }
      window.Kakao.Auth.login({
        success: function () {
          window.Kakao.API.request({
            url: '/v2/user/me',
            data: {
              property_keys: ["kakao_account.email"]
            },
            success: async function (response) {
              console.log(response);
            },
            fail: function (error) {
              console.log(error)
            },
          })
        },
        fail: function (error) {
          console.log(error)
        },
      })
    };

    const gooleLogin = function () {
      // console.log("클릭확인======");
      window.open(
        "https://accounts.google.com/o/oauth2/v2/auth?client_id=175347996726-be8037dqnukl23ddkjd89mh263oqbglf.apps.googleusercontent.com&redirect_uri=http://localhost:9090/api/login/google/auth&response_type=code&scope=email%20profile%20openid%20https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fdrive.file&access_type=offline",
        "",
        "width=450,height=550,left=800,scrollbars=yes"
      );
    };

    const naverLogin = function () {
      // console.log("클릭확인======");
      window.open(
        "https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=A7tIiaiDehHKy5pdtPsi&redirect_uri=http://127.0.0.1:9090/api/login/naver/auth",
        "",
        "width=450,height=550,left=800,scrollbars=yes"
      );
    };

    return {
      handleLogin,
      gooleLogin,
      state,
      naverLogin,
      kakaoLogin,
      props,
    };
  },
};
</script>

<style scoped src="../assets/css/login.css"></style>