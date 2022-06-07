<template>
  <div class="main">
    <h3>찜 목록</h3>

    <div class="empty" v-if="state.empty">
      <img :src="state.oops" style="width:40px" />
      <p class="p1">찜 목록이 없습니다.</p>
      <p class="p2">나에게 맞는 재능을 찾아보세요.</p>
      <v-btn class="btn"><router-link class="btn_a" to="/trade2">마켓보러 가기</router-link></v-btn>
    </div>

    <section v-if="state.empty2">
      <!-- 게시판 -->
      <div class="main2">
        <div class="d-flex mb-6" style="margin-top: 30px"></div>
        <div class="gridbox">
          <div class="helpme" v-for="tmp,idx in state.list" :key="tmp">
            <!-- <div class="close_box">
              <img :src="state.close" />
            </div> -->
            <ul
              class="helpmelist"
              style="margin-right: 10px; margin-left: 10px"
            >
              <li>
                <a href="#">
                  <div class="profile">
                    <div class="profile-item">
                      <v-img
                        src="../../assets/images/user.png"
                        style="width: 25px; height: 25px"
                      />
                    </div>
                    <p class="nickname">{{tmp.unickname}}</p>
                  </div>
                </a>
                <a href="#" class="imghover">
                  <div class="wrphover">
                    <div class="thumbnail">
                      <img :src=state.boardImgSrc[idx]  style="width: 100%; height:100%;"/>
                      </div>
                  </div>
                  <div class="profilebottom">
                    <div class="check">
                      <v-img
                        src="../../assets/images/check.png"
                        style="width: 30px; height: 30px"
                      />
                    </div>
                    <div class="profilebottom-item">
                      <p>{{tmp.btitle}}</p>
                    </div>
                  </div>

                  <div class="location">
                    <div class="location-item">
                      <v-img
                        src="../../assets/images/location.png"
                        style="width: 20px; height: 20px; margin-bottom: 40px"
                      />
                    </div>
                    <div class="address"><p>{{tmp.baddress}}</p></div>
                  </div>
                </a>
              </li>
            </ul>
          </div>
        </div>

        <!-- 페이지네이션 -->
        <div class="pagenation">
          <span v-for="tmp of state.page" :key="tmp">
            <div
              @click="clickpage(tmp)"
              style="display: inline-block; cursor: pointer; margin-left: 5px"
              :href="`/AbilityMarket/api/trade/helpMe?page=${state.page}&brole=${
                state.brole
              }&incategory=${state.selectcategory.split(' ')[0]}&inname=${
                state.selectcategoryname
              }`"
            >
              {{ tmp }}
            </div>
          </span>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
import axios from 'axios';
import { reactive } from "@vue/reactivity";
import { onMounted } from "@vue/runtime-core";
export default {
  setup() {
    const state = reactive({
      oops : require("../../assets/images/oops.png"),
      close: require("../../assets/images/close.png"),
      boardImgSrc : [],

    });

    const handleData = async()=>{
      const url = "/AbilityMarket/api/mypage/bolikeList";
      const headers = {"content-type":"application/json",
      "token": sessionStorage.getItem("TOKEN")};
      const response = await axios.get(url,{headers});
      console.log(response);
      if(response.data.status ===200){
        state.empty2 = true;
        state.list = response.data.list
        for(let i =0; i< state.list.length; i++){
          state.boardImgSrc.push(`/AbilityMarket/api/board/image?bno=${state.list[i].bno}`)
        }
      }
      else{
        state.empty = true;
      }
    }

    
    onMounted(() => {
      handleData();
    });

    return {
      state,
    };
  },
};
</script>

<style scoped src="../../assets/css/list2.css"></style>