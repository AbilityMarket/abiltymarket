<template>
  <div class="joincontainer">
    <div class="joininfo">
      <h2>회원가입</h2>

      <div class="joinbox" style="margin-top: 10px">
        <div class="textbox">
          <input type="text" v-model="state.uid" required />
          <span></span>
          <label>아이디</label>
          <button class="btn_idcheck" @click="idCheck">중복확인</button>
        </div>

        <div class="textbox">
          <input type="password" v-model="state.upw" required />
          <span></span>
          <label>비밀번호</label>
        </div>

        <div class="textbox">
          <input type="text" v-model="state.uname" required />
          <span></span>
          <label>이름</label>
        </div>

        <div class="textbox">
          <input type="text" v-model="state.unickname" required />
          <span></span>
          <label>닉네임</label>
        </div>

        <div class="textbox">
          <input type="text" v-model="state.uphone" required />
          <span></span>
          <label>전화번호</label>
        </div>

        <div class="textbox">
          <input type="text" v-model="state.uaddress" required />
          <span class="span_addr"></span>
          <label>주소</label>
          <button class="btn_addr" @click="showApi">설정</button>
        </div>

        <div class="simplejoin" style="margin-top: 40px">
          <dt class="m_hide"><span>SNS 계정으로 간편 가입</span></dt>

          <div class="sns">
            <div class="snsitem">
              <button class="kakao" @click="kakaoJoin()">
                <v-img
                  src="../assets/images/kakao.png"
                  style="width: 35px; margin-left: 7px"
                />
              </button>
            </div>
            <div class="snsitem">
              <button class="google" @click="gooleJoin()">
                <v-img
                  src="../assets/images/google.png"
                  style="width: 37px; margin-left: 6px"
                />
              </button>
            </div>
            <div class="snsitem">
              <button class="naver" @click="naverJoin()">
                <v-img
                  src="../assets/images/naver.png"
                  style="width: 37px; margin-left: 6px"
                />
              </button>
            </div>
          </div>
        </div>
        <button class="btn_next" @click="handleNext">다음으로</button>
      </div>
    </div>
  </div>
</template>

<script>
import {useRouter} from 'vue-router';
import axios from "axios";
import { reactive } from "@vue/reactivity";
export default {
  setup() {
    const router = useRouter();
    const state = reactive({
      uid: "",
      upw: "",
      uname: "",
      uphone: "",
      uaddress: "",
      unickname: "",
      ucode: "",
      ulatitude: "",
      ulongitude: "",
    });

    // 아이디 중복 체크
    const idCheck = async () => {
      if (state.uid === "") {
        alert("아이디를 입력해주세요.");
        return false;
      }
      const url = `/ROOT/api/member/check?uid=${state.uid}`;
      const headers = {
        "Content-Type": "application/json",
      };
      const response = await axios.get(url, { headers });
      console.log(response.data);
      if (response.data.status === 0) {
        alert("아이디 사용이 가능합니다.");
      } else if (response.data.status === 200) {
        alert("이미 사용중인 아이디입니다.");
      }
    };

    const handleNext = async () => {
      if (state.uid === "") {
        alert("아이디를 입력해주세요.");
        return false;
      }
      if (state.upw === "") {
        alert("암호를 입력하세요.");
        return false;
      }
      if (state.uname === "") {
        alert("이름을 입력하세요.");
        return false;
      }
      if (state.unickname === "") {
        alert("닉네임을 입력하세요.");
        return false;
      }
      if (state.uphone === "") {
        alert("전화번호를 입력하세요.");
        return false;
      }
      if (state.uaddress === "") {
        alert("주소를 설정해주세요.");
        return false;
      }

      const url = `/ROOT/api/member/join`;
      const headers = { "Content-Type": "form-data" };
      const body = new FormData();
      body.append("uid", state.uid);
      body.append("upw", state.upw);
      body.append("uname", state.uname);
      body.append("uphone", state.uphone);
      body.append("uaddress", state.uaddress);
      body.append("unickname", state.unickname);
      body.append("ulatitude", state.ulatitude);
      body.append("ulongitude", state.ulongitude);

      const response = await axios.post(url, body, { headers });
      console.log(response);

      if (response.data.status === 200) {
        handleAddr();
        
      }
    };

    // 주소 저장
    const handleAddr = async () => {
      const url = `/ROOT/api/memaddr/insertmemaddr?uid=${state.uid}`;
      const headers = {
        "Content-Type": "application/json",
      };
      const body= new FormData();
      body.append("uaddress", state.uaddress)
      body.append("ulongitude", state.ulongitude)
      body.append("ulatitude", state.ulatitude)
      // body.append("uaddress", state.uaddress)
      //  = response.data.uaddress;
        // state.ucode = response.data.ucode;
        // state.ulongitude = response.data.ulongitude;
        // state.ulatitude = response.ulatitude;
      const response = await axios.post(url, body, { headers });
      console.log(response);
      if (response.data.status === 200) {
        alert("다음페이지로 넘어갑니다.");
        router.push({ name: "JoinNext" });
      }
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
          console.log(fullRoadAddr);
          state.zip = data.zonecode; //5자리 새우편번호 사용
          state.uaddress = fullRoadAddr;
          const config = {
            headers: {
              Authorization: "KakaoAK eddc9574385a3fb5f33707a8d3bfcb98",
            },
          };
          const url =
            "https://dapi.kakao.com/v2/local/search/address.json?query=" +
            state.uaddress;
          const response = await axios.get(url, config);
          console.log(response);
          console.log(response.data.documents[0].x);
          state.ulongitude = response.data.documents[0].x;
          state.ulatitude = response.data.documents[0].y;
        },
      }).open();
    };

    const kakaoJoin = function () {
      window.Kakao.init("0eb4283842adc8eae97cfa6e89a19036");
      if (window.Kakao.Auth.getAccessToken()) {
        window.Kakao.API.request({
          url: "/v1/user/unlink",
          success: function (response) {
            console.log(response);
          },
          fail: function (error) {
            console.log(error);
          },
        });
        window.Kakao.Auth.setAccessToken(undefined);
      }
      window.Kakao.Auth.login({
        success: function () {
          window.Kakao.API.request({
            url: "/v2/user/me",
            data: {
              property_keys: ["kakao_account.email"],
            },
            success: async function (response) {
              console.log(response);
            },
            fail: function (error) {
              console.log(error);
            },
          });
        },
        fail: function (error) {
          console.log(error);
        },
      });
    };

    const gooleJoin = function () {
      window.open(
        "https://accounts.google.com/o/oauth2/v2/auth?client_id=175347996726-be8037dqnukl23ddkjd89mh263oqbglf.apps.googleusercontent.com&redirect_uri=http://localhost:9090/api/login/google/auth&response_type=code&scope=email%20profile%20openid%20https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fdrive.file&access_type=offline",
        "",
        "width=450,height=550,left=800,scrollbars=yes"
      );
    };

    const naverJoin = function () {
      window.open(
        "https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=A7tIiaiDehHKy5pdtPsi&redirect_uri=http://127.0.0.1:9090/api/login/naver/auth",
        "",
        "width=450,height=550,left=800,scrollbars=yes"
      );
    };

    return {
      state,
      handleNext,
      idCheck,
      showApi,
      gooleJoin,
      naverJoin,
      kakaoJoin,
    };
  },
};
</script>

<style scoped src="../assets/css/join.css"></style>
