<template>
  <v-app>
    <v-main>
      <v-container class="container" style="min-width: 1168px; width: 70%">
        {{ state.clickLogged }}
        {{ logged }}
        <div class="d-flex flex-row-reverse">
          <div class="top">
            <router-link to="/login"
              ><button
                class="btn_login"
                v-if="!logged"
                @click="handleMenu('login')"
              >
                로그인
              </button></router-link
            >
            <router-link to="/logout"
              ><button
                class="btn_logout"
                v-if="logged"
                @click="handleMenu('logout')"
              >
                로그아웃
              </button></router-link
            >
            <router-link to="/join">
              <button
                class="btn_join"
                v-if="!logged"
                @click="handleMenu('join')"
              >
                회원가입
              </button></router-link
            >
            <router-link to="/boardwrite">
              <button
                class="btn_write"
                v-if="logged"
                @click="handleMenu('boardwrite')"
              >
                글쓰기
              </button></router-link
            >
          </div>
        </div>

        <!-- {{state.state.logged}}
          {{state.logged}} -->

        <header v-if="!state.clickLogged">
          <!-- <header> -->
          <div class="d-flex mb-6">
            <div class="logo">
              <a href="#"><v-img src="./assets/images/logo.jpg"></v-img></a>
            </div>

            <div class="searchbox" style="margin-top: 8px">
              <input
                type="search"
                placeholder="주변에&ldquo;&nbsp;&rdquo; 할 수 있는 사람?"
              />
            </div>
            <button class="btn_search" style="margin-top: 3px">
              <v-img
                src="./assets/images/searchicon.png"
                style="width: 18px"
              ></v-img>
            </button>

            <div class="item" :class="'ml-auto'" style="margin-top: 10px">
              <ul class="menu">
                <li>
                  <a href="#"
                    ><router-link to="trade2">나의&nbsp;능력</router-link></a
                  >
                </li>
                <li style="margin-left: 30px">
                  <a href="#">당신의&nbsp;능력</a>
                </li>
                <li><a href="#">고객센터</a></li>
                <li>
                  <v-row v-if="logged">
                    <v-menu bottom min-width="400px" rounded offset-y>
                      <template v-slot:activator="{ props }">
                          <v-btn size="25" icon v-bind="props" @click="alertList">
                              <v-avatar color="purple" size="25">
                                <span class="text-h6">{{state.alertCnt}}</span>
                              </v-avatar>
                          </v-btn>
                      </template>
                      <v-card>
                          <v-list>
                              <v-list-item-group v-if="state.items">
                                  <v-list-item v-for="tmp of state.items" :key="tmp">
                                      <v-list-item-content v-text="tmp.almessage" style="cursor:pointer;" @click="clickOne(tmp.alno)"></v-list-item-content>
                                  </v-list-item>
                              </v-list-item-group>
                          </v-list>
                      </v-card>
                    </v-menu>
                  </v-row>알림
                </li>
                <li>
                  <a href="#"><router-link to="chat2">채팅</router-link></a>
                </li>
                <li>
                  <a href="#"
                    ><router-link to="mypage3">마이페이지</router-link></a
                  >
                </li>
              </ul>
            </div>
          </div>
        </header>

        <div class="main">
          <router-view></router-view>
        </div>

        <!-- 
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
          <div class="footer">
          footer
        </div>
         -->
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
import mqtt from 'precompiled-mqtt';
import { computed, onMounted, reactive, watch } from "vue";
import Swal from "sweetalert2";
import { useRouter } from "vue-router";
import { useRoute } from "vue-router";
import { useStore } from "vuex";
import axios from 'axios';

export default {
  setup() {
    const route = useRoute();
    const router = useRouter();
    const store = useStore();

    const state = reactive({
      clickLogged: computed(() => store.getters.getClicklogged),
      client: "", //접속한 클라이언트 객체
      host: "1.234.5.158", //서버주소
      port: 11884, //web용
      options: {
        clean: true, //세션초기화
        reconnectPeriod: 20000, // 주기적인 접속 시간
        // 고유값
        clientId: "d202_" + new Date().getTime(),
        username: "ds606", // 아이디
        password: "ds606", // 암호
      },

      topic: "ds/abilitymarket/unlogged/#",
      qos: 0, //quality of service 0부터 2까지의 숫자
      // 상대방에게 정확하게 보내는 수치, 중요한 건 2번으로 보내야함. 대신 리소스를 많이 쓰게 됨.

      // 알림 badge 관련
      token : sessionStorage.getItem("TOKEN"),
      alertCnt : "",
    });

    // 알림 개수
    const alertCnt = async() => {
      const url = `/ROOT/api/alert/alunreadcnt`;
      const headers = {
        "Content-Type" : "application/json",
        "token" : state.token
      };        
      const response = await axios.get(url, {headers});
      // console.log(response.data.result);
      if(response.data.status ===200) {
          // 알림 개수
          state.alertCnt = response.data.result;
      }
    };

    // 배지 클릭 후 알림 목록
    const alertList = async() => {
      console.log("확인------");
      const url = `/ROOT/api/alert/alreadlist`;
      const headers = {
        "Content-Type" : "application/json",
        "token" : state.token                
      };
      const response = await axios.get(url, {headers});
      console.log(response);
      // console.log(response.data.list[0].almessage);
      if(response.data.status === 200) {
          state.items = response.data.list;
      }
    };

    // 해당 알림 클릭
    const clickOne = async (alno) => {
      console.log("확인==="+alno);
      const url = `/ROOT/api/alert/alselectone?alno=${state.alno}`;
      const headers = {
        "Content-Type" : "application/json",
        "token" : state.token
      };
      const response = await axios.get(url, {headers});
      console.log(response);
      if(response.data.satus === 200) {
          // router.push(response.data.list.alurl);
          router.push({name:'Home'});
      }
    };

    // 채팅 연결
    const createConnection = () => {
      console.log("App.vue 채팅연결")
      const url = `ws://${state.host}:${state.port}`;
      try {
        state.client = mqtt.connect(url, state.options);
        console.log(state.client);
        state.client.on("connect", () => {
          console.log("connect success!");
          console.log("app.vue현재 구독중"+ state.topic);
        });

        state.client.on("error", () => {
          console.log("connect error!!");
        });

        state.client.on("message", (topic, message) => {
          console.log(topic, JSON.parse(message));
        });
      } catch (e) {
        console.log("mqtt error", e);
      }
      // doSubscribe()
    };

    // 토픽설정하기
    const doSubscribe = () => {
      state.client.subscribe(state.topic, { qos: state.qos }, (error, res) => {
        if (error) {
          console.log("subscribe topic error", error);
          return;
        }
        console.log("subscribe success", res);
      });
    };

    // 연결끊기
    const doUnSubscribe = () => {
      state.client.unsubscribe(state.topic, (error) => {
        if (error) {
          console.log("unsubscribe topic error", error);
          return;
        }
        console.log("unsubscribe success");
      });
    };

    // 메세지 보내기
    const sendMessage = () => {
      // json object => string : JSON.stringify()
      // string => json object : JSON.parse()

      const payload = JSON.stringify({ userid: "", msg: state.message });

      // 보낼 토픽, 보내는내용(문자), qos(0~2)
      state.client.publish(
        `ds/abilitymarket/customer/xx`,
        payload,
        0,
        (error) => {
          if (error) {
            console.log("publish error", error);
          }
        }
      );
    };

    // stores의 getters 호출
    let logged = computed(() => store.getters.getLogged);

    watch(() => (logged), (currentValue, oldValue) => {
            console.log("11111111111111111111111111111111111111111111111111",currentValue);
            console.log(oldValue);
            if(sessionStorage.getItem("UID") ===null){
              state.topic = "ds/abilitymarket/unlogged/#"
            }
            else{
              state.topic ="ds/abilitymarket/" + sessionStorage.getItem("UID")+ '/#';
                console.log("watch topic", state.topic);
            }
                
                
                // createConnection();
            // }
        },
        {
            immediate : true,
            deep:true
        }
        );

    const handleMenu = (menu) => {
      console.log(menu);
      if (menu === "login" || menu === "join") {
        sessionStorage.setItem("CLICKLOGGED", true);
        store.commit("setClicklogged", true);
      } else {
        sessionStorage.setItem("CLICKLOGGED", false);
        store.commit("setClicklogged", false);
      }

      console.log("App.vue => handleMenu => ", menu);
      router.push(menu);
    };

    // 실시간 알림창 전체 설정
    const Toast = Swal.mixin({
      toast: true,
      position: "top-end",
      showConfirmButton: false,
      timer: 3000,
      timerProgressBar: true,
      didOpen: (toast) => {
        toast.addEventListener("mouseenter", Swal.stopTimer);
        toast.addEventListener("mouseleave", Swal.resumeTimer);
      },
    });

    const subscribeUrl = `/ROOT/api/alert/sub`;

    // 생명주기 (F5를 눌러야 수행, 새로고침이 수행됨, 한번만 가능)
    onMounted(() => {
      
      // 로그인페이지나 회원가입 페이지일 경우 위에 헤더 없애기
      if (
        window.location.href.split("/")[4] === "login" ||
        window.location.href.split("/")[4] === "join"
      ) {
        sessionStorage.setItem("CLICKLOGGED", true);
        console.log("here");
        store.commit("setClicklogged", true);
      } 
      // 나머지 페이지 헤더 나오기
      else {
        sessionStorage.setItem("CLICKLOGGED", false);
        console.log("here2");
        store.commit("setClicklogged", false);
      }
      console.log("route.name", window.location.href.split("/")[4]);

      console.log(sessionStorage.getItem("TOKEN"));

      // 로그인이 되어있을경우
      if (sessionStorage.getItem("TOKEN") !== null) {
        let token = sessionStorage.getItem("TOKEN");
        state.topic = "ds/abilitymarket/"+ sessionStorage.getItem("UID");
        let eventSource = new EventSource(subscribeUrl + "?TOKEN=" + token);
        
        eventSource.addEventListener("connect", function (event) {
          console.log(event.data);
        });

        eventSource.addEventListener(
          "sendAnswerAlert",
          function (event) {
            let message = event.data;
            Toast.fire({
              icon: "info",
              title: message,
            });
          },
          false
        );

        eventSource.addEventListener(
          "sendReviewAlert",
          function (event) {
            let message = event.data;
            Toast.fire({
              icon: "info",
              title: message,
            });
          },
          false
        );

        eventSource.addEventListener(
          "sendCommAlert",
          function (event) {
            let message = event.data;
            Toast.fire({
              icon: "info",
              title: message,
            });
          },
          false
        );

        eventSource.addEventListener(
          "sendRecommentAlert",
          function (event) {
            let message = event.data;
            Toast.fire({
              icon: "info",
              title: message,
            });
          },
          false
        );

        eventSource.addEventListener(
          "sendRankUpAlert",
          function (event) {
            let message = event.data;
            Toast.fire({
              icon: "info",
              title: message,
            });
          },
          false
        );

        // 10분(600000) 후에 알림 (지금은 9초로 설정)
        eventSource.addEventListener(
          "sendInsertReviewAlert",
          function (event) {
            let message = event.data;
            setTimeout(function () {
              Toast.fire({
                icon: "info",
                title: message,
              });
            }, 9000);
            // }, 600000);
          },
          false
        );

        eventSource.addEventListener(
          "sendInterestAlert",
          function (event) {
            let message = event.data;
            Toast.fire({
              icon: "info",
              title: message,
            });
          },
          false
        );

        eventSource.addEventListener(
          "error",
          function (event) {
            eventSource.close();
            console.log(event);
          },
          false
        );

        store.commit("setLogged", true);
        state.topic = "ds/abilitymarket/" + sessionStorage.getItem("UID") +"/#";
        console.log("state.topic 로그인 되어있을경우 sessionstoragfe UID를 통해 가져와서 세팅한"+state.topic)

        // 알림 badge
        alertCnt();
        
      } else {
        state.topic = "ds/abilitymarket/unlogged/#"
        store.commit("setLogged", false);
      }

      // createConnection()

    });

    return {
      handleMenu,
      logged,
      state,
      // createConnection,
      // doSubscribe,
      // doUnSubscribe,
      // sendMessage,
      alertList,
      clickOne,
    };
  },
};
</script>

<style scoped>
@import "./assets/css/default.css";

@font-face {
  font-family: "GmarketSansMedium";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/GmarketSansMedium.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}

@font-face {
  font-family: "GmarketSansLight";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/GmarketSansLight.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}

@font-face {
  font-family: "Vitro_core";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-10-21@1.0/Vitro_core.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}

ul,
li {
  list-style: none;
}

a {
  text-decoration: none;
  color: #707070;
  line-height: 100%;
}

button {
  all: unset;
}

.logo {
  width: 127px;
}
.container {
  font-family: "GmarketSansMedium";
}

.top {
  text-align: center;
  margin-top: 10px;
  margin-bottom: 10px;
}

.btn_login {
  all: unset;
  width: 125px;
  height: 35px;
  border: 1px solid #3476d8;
  border-radius: 15px;
  font-size: 0.9rem;
  cursor: pointer;
  margin-right: 10px;
}

.btn_logout {
  all: unset;
  width: 125px;
  height: 35px;
  border: 1px solid #3476d8;
  border-radius: 15px;
  font-size: 0.9rem;
  cursor: pointer;
  margin-right: 10px;
}

.btn_join {
  all: unset;
  width: 125px;
  height: 35px;
  background-color: #3476d8;
  border: 1px solid #3476d8;
  border-radius: 15px;
  font-size: 0.9rem;
  cursor: pointer;
  color: #ffffff;
}

.btn_write {
  all: unset;
  width: 125px;
  height: 35px;
  background-color: #3476d8;
  border: 1px solid #3476d8;
  border-radius: 15px;
  font-size: 0.9rem;
  cursor: pointer;
  color: #ffffff;
}

.menu > li {
  display: inline-block;
  margin-left: 30px;
  font-size: 1rem;
}

.searchbox {
  margin-left: 100px;
}

input {
  border-bottom: 2px solid #3476d8;
  border-left: none;
  border-right: none;
  border-top: none;
  font-size: 16px;
  font-family: "GmarketSansLight";
}
input:focus {
  outline: none;
}
input::placeholder {
  color: #6797dd;
}
</style>

<style>
.swal2-icon.swal2-info {
  border-color: #3476d8 !important;
  color: #3476d8 !important;
}
</style>
