<template>
  <div class="logincontainer">
    <div class="logo">
      <v-img src="../assets/images/logo.jpg" style="width: 127px" />
    </div>

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

        <button class="btn_login" @click="handleLogin">
          로그인
        </button>
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
          <button class="kakao">
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
import { reactive } from '@vue/reactivity';
import { useStore } from "vuex";
export default {
  setup() {
    const store = useStore();
    const router = useRouter();

    const state = reactive({
      uid:'',
      upw:''
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
        sessionStorage.setItem("UID",state.uid);
        router.push({ name: "Home" });
      }
    };

    const gooleLogin = function () {
      // console.log("클릭확인======");
      window.open("https://accounts.google.com/o/oauth2/v2/auth?client_id=175347996726-be8037dqnukl23ddkjd89mh263oqbglf.apps.googleusercontent.com&redirect_uri=http://localhost:9090/api/login/google/auth&response_type=code&scope=email%20profile%20openid%20https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fdrive.file&access_type=offline",
                    "","width=450,height=550,left=800,scrollbars=yes");
    };

        const naverLogin = function () {
      // console.log("클릭확인======");
      window.open("https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=A7tIiaiDehHKy5pdtPsi&redirect_uri=http://127.0.0.1:9090/api/login/naver/auth",
                  "","width=450,height=550,left=800,scrollbars=yes");
    };


    return {
      handleLogin,
      gooleLogin,
      state,
      naverLogin
    };
  },
};
</script>

<style lang="css" scoped>
.logincontainer {
  font-family: "GmarketSansLight";
  position: absolute;
  display: flex;
  flex-direction: column;
  top: 20%;
  left: 50%;
  transform: translate(-50%);
}

small {
  font-family: "GmarketSansLight";
  font-size: 13px;
}

.logo {
  margin: 0 auto;
  margin-top: 30px;
  margin-bottom: 30px;
}

.textbox {
  position: relative;
  margin: 0 auto;
  width: 380px;
  border-bottom: 1px solid #afafaf;
  margin-bottom: 20px;
  align-items: center;
}

.textbox > input {
  width: 50%;
  padding: 0 5px;
  height: 40px;
  font-size: 15px;
  border: none;
  background: none;
  outline: none;
  color: #000000;
  font-family: "GmarketSansMedium";
}

.textbox > label {
  position: absolute;
  top: 50%;
  left: 5px;
  color: #afafaf;
  transform: translateY(-50%);
  font-size: 15px;
  pointer-events: none;
  transition: 0.5s;
  font-family: "GmarketSansMedium";
}

.textbox > span::before {
  content: "";
  position: absolute;
  top: 40px;
  left: 0;
  width: 0%;
  height: 2px;
  background: #3476d8;
  transition: 0.5s;
}

.textbox input:focus ~ label,
.textbox input:valid ~ label {
  top: -5px;
  color: #3476d8;
  font-size: 14px;
  font-family: "GmarketSansMedium";
}

.textbox input:focus ~ span::before,
.textbox input:valid ~ span::before {
  width: 100%;
}

/* valid */


/* 로그인 버튼 */
.btn_login {
  all: unset;
  width: 380px;
  height: 45px;
  font-size: 15px;
  background-color: #3476d8;
  color: #ffffff;
  font-family: "GmarketSansLight";
  border-radius: 5px;
  text-align: center;
  margin-bottom: 5px;
  cursor: pointer;
}

input[type="checkbox"] {
  width: 15px;
  height: 15px;
  margin: 5px;
  margin-bottom: 8px;
}

.check {
  display: flex;
  align-items: center;
}

.idsave {
  font-family: "GmarketSansLight";
  font-size: 14px;
  color: #808080;
  display: flex;
  align-items: center;
}

.checklink > a {
  font-size: 14px;
  color: #808080;
  font-family: "GmarketSansLight";
}

.findpw::before {
  content: "|";
  color: #808080;
  margin-right: 10px;
}

/* 간편 로그인 */
.simplelogin dt > span {
  display: inline-block;
  background-color: #fff;
  position: relative;
  z-index: 10;
  padding: 0 10px;
  color: #afafaf;
  font-family: "GmarketSansMedium";
}

.simplelogin > dt {
  text-align: center;
  font-size: 14px;
  line-height: 52px;
  color: #808080;
  position: relative;
  font-family: "GmarketSansLight";
}

.simplelogin dt::after {
  content: "";
  width: 100%;
  height: 1px;
  position: absolute;
  left: 0;
  top: 50%;
  background: #afafaf;
}

.sns {
  display: flex;
  flex-direction: row;
  justify-content: center;
  gap: 20px;
}

.snsitem {
  margin-bottom: -10px;
}

.kakao {
  background: #ffc20f;
  width: 50px;
  height: 50px;
  border-radius: 50px;
}

.naver {
  background: #1ec800;
  width: 50px;
  height: 50px;
  border-radius: 50px;
}

.google {
  background: #e6e6e6;
  width: 50px;
  height: 50px;
  border-radius: 50px;
}

.btn_join {
  all: unset;
  width: 350px;
  height: 40px;
  font-size: 14px;
  text-align: center;
  border: 1px solid #0258da;
  color: #0258da;
  font-family: "GmarketSansMedium";
  border-radius: 5px;
  text-align: center;
  cursor: pointer;
}

.join {
  display: flex;
  justify-content: center;
  margin-top: 30px;
}
</style>
