<template>
  <div class="main" v-if="state">
    <h3>내가 쓴 글</h3>
    <section>
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
                <a href="#" class="imghover">
                  <div class="wrphover">
                    <div class="thumbnail">
                      <img :src=state.boardImgSrc[idx]  style="width: 100%; height:100%;"/>
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
export default {
  setup() {
    const state = reactive({
      close: require("../../assets/images/close.png"),
      boardImgSrc : [],
    });

    const handleData = async () => {
      const url = "/ROOT/api/mypage/iWrote";
      const headers = {"content-type":"application/json",
      "token": sessionStorage.getItem("TOKEN")};
      const response = await axios.get(url,{headers});
      console.log(response);
      if(response.data.status === 200){
        state.list = response.data.list
        console.log(state.list.length)
        for(let i =0; i< state.list.length; i++){
          // console.log(state.list[i].bno);
          console.log("state.boardImgSrc 넣기전",state.boardImgSrc)
          state.boardImgSrc.push(`/ROOT/api/board/image?bno=${state.list[i].bno}`)
        // state.boardImgSrc.push()
          console.log("state.boardImgSrc 넣은 후",state.boardImgsrc)
      }
      }
    };

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