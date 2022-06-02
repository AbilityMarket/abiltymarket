<template>
  <div class="main" v-if="state">
    <h3>내가 쓴 글</h3>

    <div class="empty" v-if="state.empty">
      <img :src="state.oops" style="width:40px" />
      <p class="p1">글 목록이 없습니다.</p>
      <p class="p2">새 글을 작성해주세요</p>
      <v-btn class="btn"><router-link class="btn_a" to="/trade2">작성하러 가기</router-link></v-btn>
    </div>

    <section v-if="state.empty2">
      <!-- 게시판 -->
      <div class="main2">
        <div class="d-flex mb-6" style="margin-top: 30px"></div>
        <div class="gridbox"  v-if="state.list">
          <div class="helpme" v-for="(tmp,idx) in state.list" :key="tmp">
            <!-- <div class="close_box">
              <img :src="state.close" />
            </div> -->
            <ul
              class="helpmelist"
              style="margin-right: 10px; margin-left: 10px"
            >
              <li>
                <div @click="handleBod(tmp.bno)">
                  <div class="wrphover">
                    <div class="thumbnail">
                      <img :src=state.boardImgSrc[idx] style="width: 100%; height:100%;" />
                    </div>
                    
                    <!-- <span class="new">NEW</span> -->
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
                </div>
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
              :href="`/ROOT/api/trade/helpMe?page=${state.page}&brole=${
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
import {useRouter} from 'vue-router';
//import { useRoute } from 'vue-router';

export default {
  setup() {
    const router = useRouter();
    //const route = useRoute();
    const state = reactive({
      oops : require("../../assets/images/oops.png"),
      close: require("../../assets/images/close.png"),
      boardImgSrc : [],
      token : sessionStorage.getItem("TOKEN"),
    });

    const handleData = async () => {
      const url = "/ROOT/api/mypage/iWrote";
      const headers = {"content-type":"application/json",
      "token": sessionStorage.getItem("TOKEN")};
      const response = await axios.get(url,{headers});
      console.log(response);
      if(response.data.status === 200){
        state.empty2 = true;
        state.list = response.data.list
        console.log(state.list.length)
        for(let i =0; i< state.list.length; i++){
          // console.log(state.list[i].bno);
          // console.log("state.boardImgSrc 넣기전",state.boardImgSrc)
          state.boardImgSrc.push(`/ROOT/api/board/image?bno=${state.list[i].bno}`)
        // state.boardImgSrc.push()
          // console.log("state.boardImgSrc 넣은 후",state.boardImgsrc)
        }
      }
      else if (response.data.status === 0){
        state.empty = true;
      }
    };

    onMounted(() => {
      handleData();
    });

    const handleBod = (bno) => {
      console.log(bno);
      router.push({name:'Detail', query:{bno:bno}});
    }


    return {
      state,
      handleBod
    };
  },
};
</script>

<style scoped src="../../assets/css/list2.css"></style>