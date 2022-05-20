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
          <div ref="id_error" class="id_error">일치하는 아이디가 없습니다.</div>
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
      <div class="mailsave">
        <input type="checkbox" /><span>이메일 저장</span>
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
          <button class="google">
            <v-img
              src="../assets/images/google.png"
              style="width: 37px; margin-left: 6px"
            />
          </button>
        </div>
        <div class="snsitem">
          <button class="naver">
            <v-img
              src="../assets/images/naver.png"
              style="width: 37px; margin-left: 6px"
            />
          </button>
        </div>
      </div>
    </div>

    <div class="join">
      <button class="btn_join">
        당신의 가치를 경험해보세요.
        <span style="text-decoration: underline">회원가입</span>
      </button>
    </div>
  </div>
</template>

<script>
import { useRouter } from "vue-router";
import axios from "axios";
import { reactive, ref } from "vue";
export default {
  setup() {
    const router = useRouter();

    const state = reactive({
      uid: "",
      id_error: "",
      upw: "",
      pw_error: "",
    });

    const id_error = ref(null);
    const uid = ref(null);
    const pw_error = ref(null);
    const upw = ref(null);

    const validated = () => {
      if (state.uid.length < 9) {
        id_error.value.style.color = "#ff0000";
        uid.value.focus();
        return false;
      }

      if (state.upw.length < 9) {
        pw_error.value.style.color = "#ff0000";
        upw.value.focus();
        return false;
      }
    };

    // function validated() {
    //     if (state.id.length < 9) {
    //       id_error.value.style.color = "#ff0000";
    //       id.value.focus();
    //       return false;
    //     }

    //     if (state.pw.length < 9) {
    //       pw_error.value.style.color = "#ff0000";
    //       pw.value.focus();
    //       return false;
    //     }
    //   }

    const handleLogin = async () => {
      const url = `/ROOT/api/member/login`;
      const headers = { "Content-Type": "application/json" };
      const body = {
        uid: state.uid,
        upw: state.upw,
      };
      const response = await axios.post(url, body, { headers });
      console.log(response.data);
      if (response.data.status === 200) {
        sessionStorage.setItem("TOKEN", response.data.token);
        router.push({ name: "Home" });
      }
    };

    return {
      handleLogin,
      validated,
      id_error,
      uid,
      pw_error,
      upw,
      state,
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
  position: relative;
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
.textbox .id_error,
.pw_error {
  position: absolute;
  margin-top: 5px;
  margin-left: 5px;
  font-size: 13px;
  visibility: hidden;
}

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

.mailsave {
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
